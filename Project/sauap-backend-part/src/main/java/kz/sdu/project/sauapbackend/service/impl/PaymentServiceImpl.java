package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dao.PaymentDao;
import kz.sdu.project.sauapbackend.dto.MailMessage;
import kz.sdu.project.sauapbackend.dto.PaymentResultDto;
import kz.sdu.project.sauapbackend.dto.PaymentStatus;
import kz.sdu.project.sauapbackend.dto.PaymentTypes;
import kz.sdu.project.sauapbackend.dto.request.PaymentRequestDto;
import kz.sdu.project.sauapbackend.entity.*;
import kz.sdu.project.sauapbackend.exception.CreateMessageException;
import kz.sdu.project.sauapbackend.exception.SauapProcessException;
import kz.sdu.project.sauapbackend.repository.FoundationRepository;
import kz.sdu.project.sauapbackend.repository.FundraiseRepository;
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
@Qualifier("PaymentServiceImpl")
public class PaymentServiceImpl implements PaymentService<PaymentRequestDto> {

    private final UsersRepository usersRepository;
    private final FoundationRepository foundationRepository;
    private final UserCardsRepository userCardsRepository;
    private final FundraiseRepository fundraiseRepository;
    private final PaymentDao paymentDao;
    private final MailService mailService;

    public PaymentServiceImpl(UsersRepository usersRepository, FoundationRepository foundationRepository, UserCardsRepository userCardsRepository, FundraiseRepository fundraiseRepository, PaymentDao paymentDao, MailService mailService) {
        this.usersRepository = usersRepository;
        this.foundationRepository = foundationRepository;
        this.userCardsRepository = userCardsRepository;
        this.fundraiseRepository = fundraiseRepository;
        this.paymentDao = paymentDao;
        this.mailService = mailService;
    }

    @Override
    public PaymentResultDto makePayment(PaymentRequestDto payment) throws CreateMessageException, SauapProcessException {
        Optional<Users> usersOptional = usersRepository.findById(payment.getUserId());
        if (usersOptional.isEmpty()) {
            throw new CreateMessageException(String.format("User not found by given id: %d", payment.getUserId()));
        }
        Optional<Foundation> foundationOptional = foundationRepository.findById(payment.getFoundationId());
        if (foundationOptional.isEmpty()) {
            throw new CreateMessageException(String.format("Foundation not found by given id: %d", payment.getFoundationId()));
        }
        Optional<UserCards> userCardsOptional = userCardsRepository.findById(payment.getCardId());
        if (userCardsOptional.isEmpty()) {
            throw new CreateMessageException(String.format("User card is not found by given id: %d", payment.getCardId()));
        }
        Optional<Fundraising> fundraisingOptional = fundraiseRepository.findById(payment.getFundraiseId());
        if (fundraisingOptional.isEmpty()) {
            throw new CreateMessageException(String.format("Fundraise is not found by given id: %d", payment.getFundraiseId()));
        }

        Payment newPayment = new Payment();
        newPayment.setPaymentId(IdGenerator.generateUniqueId());
        newPayment.setTypeOfPayment(PaymentTypes.DIRECT.name());
        newPayment.setPaymentStartTime(LocalDateTime.now());
        newPayment.setAmount(payment.getAmount());
        newPayment.setDescription(null);
        newPayment.setCardId(payment.getCardId());
        newPayment.setUserId(payment.getUserId());
        newPayment.setFoundationId(payment.getFoundationId());
        newPayment.setFundraiseId(payment.getFundraiseId());

        String userEmail =      usersOptional.get().getEmail();
        String userFullName =   String.format("%s %s", usersOptional.get().getFirstName(), usersOptional.get().getLastname());
        String date =           PaymentUtils.getDateString(LocalDateTime.now());
        Double amount =         payment.getAmount();
        String fundraiseName =  fundraisingOptional.get().getFundraiseTitle();
        String foundation =     foundationOptional.get().getFoundationName();

        MailMessage message;
        try {
            makePayment(newPayment, usersOptional.get());

            String contentMail = EmailConstants.getDirectDonateTemplateReceipt(newPayment.getPaymentId(), date, userFullName, fundraiseName, amount, foundation);

            message = new MailMessage();
            message.setContentType(EmailConstants.CONTENT_TYPE_HTML);
            message.setPersonal(EmailConstants.PERSONAL);
            message.setMailSubject(EmailConstants.SUBJECT_DIRECT_DONATION);
            message.setMailFrom(EmailConstants.FROM);
            message.setMailTo(userEmail);
            message.setMailContent(contentMail);
            mailService.sendMessage(message);
        } catch (Exception ex) {
            log.error("DIRECT PAYMENT | Error while process payment | PaymentId: {}", newPayment.getPaymentId(), ex);
            paymentDao.updateStatus(newPayment.getPaymentId(), PaymentStatus.FAILED, ex.getMessage());
            throw new SauapProcessException(ex.getMessage());
        }
        paymentDao.updateStatus(newPayment.getPaymentId(), PaymentStatus.DONE, null);

        return new PaymentResultDto(newPayment.getPaymentId(), message);
    }

    private void makePayment(Payment payment, Users currentUser) throws Exception {
        payment.setPaymentStartTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.CREATED.getStatus());
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


}
