package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.PaymentResultDto;
import kz.sdu.project.sauapbackend.dto.request.AddNewMonthlyDonationRequestDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserCardsResponseDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserMonthDonationResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.entity.Foundation;
import kz.sdu.project.sauapbackend.entity.UserCards;
import kz.sdu.project.sauapbackend.entity.UserDonationMonth;
import kz.sdu.project.sauapbackend.entity.Users;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.SauapProcessException;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.FoundationMapper;
import kz.sdu.project.sauapbackend.repository.FoundationRepository;
import kz.sdu.project.sauapbackend.repository.UserCardsRepository;
import kz.sdu.project.sauapbackend.repository.UserDonationMonthRepository;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.PaymentService;
import kz.sdu.project.sauapbackend.service.UserMonthlyDonateService;
import kz.sdu.project.sauapbackend.utils.CardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDonationServiceImpl implements UserMonthlyDonateService {

    private final UserDonationMonthRepository donationRepository;
    private final UsersRepository usersRepository;
    private final FoundationRepository foundationRepository;
    private final UserCardsRepository userCardsRepository;
    private final PaymentService<UserDonationMonth> paymentService;

    @Autowired
    public UserDonationServiceImpl(UserDonationMonthRepository donationRepository,
                                   UsersRepository usersRepository,
                                   FoundationRepository foundationRepository,
                                   UserCardsRepository userCardsRepository,
                                   PaymentService<UserDonationMonth> paymentService) {
        this.donationRepository = donationRepository;
        this.usersRepository = usersRepository;
        this.foundationRepository = foundationRepository;
        this.userCardsRepository = userCardsRepository;
        this.paymentService = paymentService;
    }

    @Override
    public MainCreateResponse addNewMonthlyDonation(AddNewMonthlyDonationRequestDto request) {
        Optional<Users> checkUser = usersRepository.findById(request.getUserId());
        if (checkUser.isEmpty()) {
            throw new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND);
        }
        Optional<Foundation> checkFoundation = foundationRepository.findById(request.getFoundationId());
        if (checkFoundation.isEmpty()) {
            throw new ValidationException("Foundation is not found", ErrorCode.FOUNDATION_NOT_FOUND);
        }
        Optional<UserCards> checkCard = userCardsRepository.findById(request.getCardId());
        if (checkCard.isEmpty()) {
            throw new ValidationException("Card is not found", ErrorCode.CARD_IS_NOT_FOUND);
        }

        Optional<UserDonationMonth> isAlreadyExist = donationRepository.findByUserIdAndFoundationId(request.getUserId(), request.getFoundationId());
        // if data already exist then just change the amount;
        UserDonationMonth userDonationMonth;
        if (isAlreadyExist.isPresent()) {
            userDonationMonth = isAlreadyExist.get();
        } else {
            userDonationMonth = new UserDonationMonth();
            userDonationMonth.setUserId(request.getUserId());
            userDonationMonth.setFoundationId(request.getFoundationId());
        }
        Integer today = LocalDate.now().getDayOfMonth();
        userDonationMonth.setDonateDay(today);
        userDonationMonth.setAmount(request.getAmount());
        userDonationMonth.setIsDone(false);
        userDonationMonth.setCardId(request.getCardId());
        UserDonationMonth savedDonation = donationRepository.saveAndFlush(userDonationMonth);

        // make a payment
        processMonthlyPayment(Collections.singletonList(userDonationMonth));

        return new MainCreateResponse(savedDonation.getUserDonationId());
    }

    @Override
    public Optional<List<GetUserMonthDonationResponseDto>> getUserDonationList(Long userId) {
        Optional<Users> checkUser = usersRepository.findById(userId);
        if (checkUser.isEmpty()) {
            throw new ValidationException("User not found by id", ErrorCode.USER_NOT_FOUND);
        }

        List<UserDonationMonth> userDonationMonthList = donationRepository.findAllByUserIdAndIsDoneFalse(userId);
        if (userDonationMonthList.isEmpty()) {
            return Optional.empty();
        }

        List<GetUserMonthDonationResponseDto> donationList = userDonationMonthList.stream()
                .map(data -> {
                    Foundation foundation = foundationRepository.findById(data.getFoundationId()).get();
                    UserCards card = userCardsRepository.findById(data.getCardId()).get();
                    return GetUserMonthDonationResponseDto.builder()
                            .id(data.getUserDonationId())
                            .amount(data.getAmount())
                            .foundation(FoundationMapper.toDto(foundation))
                            .cardInfo(new GetUserCardsResponseDto(data.getCardId(), CardUtils.maskCardNumber(card.getCardNumber()), "VISA"))
                            .build();
                }).collect(Collectors.toList());

        return Optional.of(donationList);
    }

    @Override
    public void processMonthlyPayment() {
        LocalDateTime today = LocalDateTime.now();
        Integer todayDayOfMonth = today.getDayOfMonth();

        List<UserDonationMonth> userDonationMonthList = donationRepository.findAllByDonateDayAndIsDoneFalse(todayDayOfMonth);
        processMonthlyPayment(userDonationMonthList);
    }

    private void processMonthlyPayment(List<UserDonationMonth> userDonationMonthList) {
        log.info("PAYMENT | User monthly donation has started | {}", userDonationMonthList);
        for (UserDonationMonth userDonationMonth : userDonationMonthList) {
            PaymentResultDto paymentAndMessage;
            try {
                paymentAndMessage = paymentService.makePayment(userDonationMonth);
            } catch (Exception ex) {
                throw new SauapProcessException(String.format("ERROR WHILE PROCESS MONTHLY PAYMENT | %s", ex.getMessage()));
            }
            log.info("PAYMENT | {} | {} successfully done", userDonationMonth, paymentAndMessage.getPaymentId());
        }
    }
}
