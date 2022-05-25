package kz.sdu.project.sauapbackend.mapper;

import kz.sdu.project.sauapbackend.entity.Payment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PaymentMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet resultSet, int i) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(resultSet.getString(Payment.PAYMENT_ID));
        payment.setTypeOfPayment(resultSet.getString(Payment.TYPE_OF_PAYMENT));
        Timestamp startTime = resultSet.getTimestamp(Payment.PAYMENT_START_TIME);
        payment.setPaymentStartTime(
                startTime == null? null : startTime.toLocalDateTime());
        payment.setStatus(resultSet.getInt(Payment.STATUS));
        payment.setAmount(resultSet.getDouble(Payment.AMOUNT));
        payment.setTransactionId(resultSet.getString(Payment.TRANSACTION_ID));
        payment.setDescription(resultSet.getString(Payment.DESCRIPTION));
        payment.setCardId(resultSet.getLong(Payment.CARD_ID));
        payment.setUserId(resultSet.getLong(Payment.USER_ID));
        payment.setFoundationId(resultSet.getLong(Payment.FOUNDATION_ID));
        Timestamp doneTime = resultSet.getTimestamp(Payment.PAYMENT_DONE_TIME);
        payment.setPaymentLastUpdateTime(
                doneTime == null? null : doneTime.toLocalDateTime());
        payment.setFundraiseId(resultSet.getLong(Payment.FUNDRAISE_ID));
        return payment;
    }
}
