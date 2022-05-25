package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.UserCardDto;
import kz.sdu.project.sauapbackend.dto.response.GetUserCardsResponseDto;
import kz.sdu.project.sauapbackend.dto.response.MainCreateResponse;
import kz.sdu.project.sauapbackend.entity.UserCards;
import kz.sdu.project.sauapbackend.entity.Users;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.mapper.UserMapper;
import kz.sdu.project.sauapbackend.repository.UserCardsRepository;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.UserCardsService;
import kz.sdu.project.sauapbackend.utils.CardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
public class UserCardsServiceImpl implements UserCardsService {

    private final UsersRepository usersRepository;
    private final UserCardsRepository userCardsRepository;

    public UserCardsServiceImpl(UsersRepository usersRepository, UserCardsRepository userCardsRepository) {
        this.usersRepository = usersRepository;
        this.userCardsRepository = userCardsRepository;
    }

    @Override
    public MainCreateResponse addNewCard(UserCardDto newUserCard, Long userId) {
        Optional<Users> currentUser = usersRepository.findById(userId);
        if (currentUser.isEmpty()) {
            throw new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND);
        }
        String cardNumber = newUserCard.getCardNumber();
        String cardHolderName = newUserCard.getCardHolderName();
        Optional<UserCards> isThereExistUserCard = userCardsRepository.findByCardNumberAndCardHolderName(cardNumber, cardHolderName);
        if (isThereExistUserCard.isPresent()) {
            throw new ValidationException(String.format("Card %s is already exist", CardUtils.maskCardNumber(cardNumber)), ErrorCode.CARD_IS_ALREADY_EXIST);
        }

        UserCards userCard = UserMapper.toEntity(newUserCard);
        userCard.setUserId(userId);
        UserCards savedUserCard = userCardsRepository.saveAndFlush(userCard);

        return new MainCreateResponse(savedUserCard.getCardId());
    }

    @Override
    public void deleteCard(Long cardId, Long userId) {
        Optional<Users> currentUser = usersRepository.findById(userId);
        if (currentUser.isEmpty()) {
            throw new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND);
        }

        Optional<UserCards> isThereCardExist = userCardsRepository.findByCardIdAndUserId(cardId, userId);
        if (isThereCardExist.isEmpty()) {
            throw new ValidationException("Invalid cardId or card doesn't exist", ErrorCode.CARD_IS_NOT_FOUND);
        }

        userCardsRepository.deleteById(cardId);
        log.info("USER CARD | Card was deleted successfully | cardId: {} | userId: {}", cardId, userId);
    }

    @Override
    public Optional<List<GetUserCardsResponseDto>> getUserCardsByUserId(Long userId) {
        Optional<Users> currentUser = usersRepository.findById(userId);
        if (currentUser.isEmpty()) {
            throw new ValidationException("User is not found", ErrorCode.USER_NOT_FOUND);
        }

        List<UserCards> userCards = userCardsRepository.findAllByUserId(userId);
        List<GetUserCardsResponseDto> response = userCards.stream()
                .map(o -> new GetUserCardsResponseDto(o.getCardId(), CardUtils.maskCardNumber(o.getCardNumber()), "VISA"))
                .collect(Collectors.toList());

        if (response.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(response);
    }


}
