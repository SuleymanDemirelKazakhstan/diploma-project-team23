package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dao.PaymentDao;
import kz.sdu.project.sauapbackend.dto.MailMessage;
import kz.sdu.project.sauapbackend.dto.PaymentResultDto;
import kz.sdu.project.sauapbackend.dto.PaymentStatus;
import kz.sdu.project.sauapbackend.dto.PaymentTypes;
import kz.sdu.project.sauapbackend.entity.*;
import kz.sdu.project.sauapbackend.exception.CreateMessageException;
import kz.sdu.project.sauapbackend.exception.SauapProcessException;
import kz.sdu.project.sauapbackend.repository.FoundationRepository;
import kz.sdu.project.sauapbackend.repository.UserCardsRepository;
import kz.sdu.project.sauapbackend.repository.UsersRepository;
import kz.sdu.project.sauapbackend.service.MailService;
import kz.sdu.project.sauapbackend.service.PaymentService;
import kz.sdu.project.sauapbackend.utils.EmailConstants;
import kz.sdu.project.sauapbackend.utils.IdGenerator;
import kz.sdu.project.sauapbackend.utils.PaymentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@Qualifier("MonthlyPaymentImpl")
public class MonthlyPaymentImpl implements PaymentService<UserDonationMonth> {

    private final PaymentDao paymentDao;
    private final UsersRepository usersRepository;
    private final FoundationRepository foundationRepository;
    private final UserCardsRepository userCardsRepository;
    private final MailService mailService;

    public MonthlyPaymentImpl(PaymentDao paymentDao, UsersRepository usersRepository,
                              FoundationRepository foundationRepository, UserCardsRepository userCardsRepository,
                              MailService mailService) {
        this.paymentDao = paymentDao;
        this.usersRepository = usersRepository;
        this.foundationRepository = foundationRepository;
        this.userCardsRepository = userCardsRepository;
        this.mailService = mailService;
    }

    @Override
    public PaymentResultDto makePayment(UserDonationMonth userDonationMonth) throws CreateMessageException, SauapProcessException {
        Optional<Users> usersOptional = usersRepository.findById(userDonationMonth.getUserId());
        if (usersOptional.isEmpty()) {
            throw new CreateMessageException(String.format("User not found by this id: %d", userDonationMonth.getUserId()));
        }
        Optional<Foundation> foundationOptional = foundationRepository.findById(userDonationMonth.getFoundationId());
        if (foundationOptional.isEmpty()) {
            throw new CreateMessageException(String.format("Foundation not found by this id: %d", userDonationMonth.getFoundationId()));
        }
        Optional<UserCards> userCardsOptional = userCardsRepository.findById(userDonationMonth.getCardId());
        if (userCardsOptional.isEmpty()) {
            throw new CreateMessageException(String.format("User card is not found by this id: %d", userDonationMonth.getCardId()));
        }

        String userEmail =      usersOptional.get().getEmail();
        String userFullName =   String.format("%s %s", usersOptional.get().getFirstName(), usersOptional.get().getLastname());
        String date =           PaymentUtils.getDateString(LocalDateTime.now());
        Double amount =         userDonationMonth.getAmount();
        String foundationName = foundationOptional.get().getFoundationName();

        Payment newPayment = createPayment(amount, userDonationMonth.getUserId(), userDonationMonth.getFoundationId(), userDonationMonth.getCardId());
        MailMessage message;
        try {
            // TODO: logic for make a payment ---------------------------------------------------------------------------
            makePayment(newPayment, usersOptional.get());
            // ----------------------------------------------------------------------------------------------------------
            String contentMail = EmailConstants.getMonthlyDonateTemplateReceipt(userFullName, newPayment.getPaymentId(), date, foundationName, amount);

            message = new MailMessage();
            message.setContentType(EmailConstants.CONTENT_TYPE_HTML);
            message.setPersonal(EmailConstants.PERSONAL);
            message.setMailSubject(EmailConstants.SUBJECT_MONTHLY_DONATION);
            message.setMailFrom(EmailConstants.FROM);
            message.setMailTo(userEmail);
            message.setMailContent(contentMail);
            mailService.sendMessage(message);
        } catch (Exception ex) {
            log.error("MONTHLY PAYMENT | Error while process payment | PaymentId: {}", newPayment.getPaymentId(), ex);
            paymentDao.updateStatus(newPayment.getPaymentId(), PaymentStatus.FAILED, ex.getMessage());
            throw new SauapProcessException(ex.getMessage());
        }
        paymentDao.updateStatus(newPayment.getPaymentId(), PaymentStatus.DONE, null);

        return new PaymentResultDto(newPayment.getPaymentId(), message);
    }

    private void makePayment(Payment payment, Users currentUser) throws Exception {
        payment.setPaymentStartTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.CREATED.getStatus());
        payment.setFundraiseId(null);
        paymentDao.create(payment);

        /**
         * TODO: 1. There are integration with payment system
         * TODO: 2. Save payment with CREATED STATUS and get transactionId from payment system
         */

        BigDecimal donatedCurrentAmount = currentUser.getDonatedAmounts();
        donatedCurrentAmount = donatedCurrentAmount.add(BigDecimal.valueOf(payment.getAmount()));
        currentUser.setDonatedAmounts(donatedCurrentAmount);
        usersRepository.saveAndFlush(currentUser);
    }

    private Payment createPayment(Double amount, Long userId, Long foundationId, Long cardId) {
        Payment payment = new Payment();
        payment.setPaymentId(IdGenerator.generateUniqueId());
        payment.setTypeOfPayment(PaymentTypes.MONTHLY.name());
        payment.setAmount(amount);
        payment.setDescription(null);
        payment.setCardId(cardId);
        payment.setUserId(userId);
        payment.setFoundationId(foundationId);
        return payment;
    }

}
