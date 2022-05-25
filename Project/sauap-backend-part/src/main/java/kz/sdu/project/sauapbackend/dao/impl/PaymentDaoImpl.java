package kz.sdu.project.sauapbackend.dao.impl;

import kz.sdu.project.sauapbackend.dao.Dao;
import kz.sdu.project.sauapbackend.dao.PaymentDao;
import kz.sdu.project.sauapbackend.dto.PaymentStatus;
import kz.sdu.project.sauapbackend.entity.Payment;
import kz.sdu.project.sauapbackend.exception.SauapProcessException;
import kz.sdu.project.sauapbackend.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;


@Slf4j
@Dao
@Primary
public class PaymentDaoImpl implements PaymentDao {

    //------------------------------------------------------------------------------------------------------------------
    private static final String CREATE_PAYMENT =
            "insert into payments (payment_id, type_of_payment, payment_start_time, status, " +
            "                       amount, transaction_id, description, card_id,           " +
            "                       user_id, foundation_id, fundraise_id)                   " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)                                       ";
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    private static final String UPDATE_STATUS =
            "update payments                                                 " +
            "set status = ?, payment_last_update_time = ?, error_message = ? " +
            "where payment_id = ?                                            ";
    //------------------------------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------------------------------
    private static final String SELECT_PAYMENTS_BY_ID =
            "select payment_id, type_of_payment, payment_start_time,        " +
            "       status, amount, transaction_id, description, card_id,   " +
            "       user_id, foundation_id, payment_done_time, fundraise_id " +
            "from payments                                                  " +
            "where payment_id = ?                                           ";
    //------------------------------------------------------------------------------------------------------------------

    private final DataSource dataSource;

    public PaymentDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Payment payment) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_PAYMENT)) {
            statement.setString(1, payment.getPaymentId());
            statement.setString(2, payment.getTypeOfPayment());
            statement.setTimestamp(3,
                    payment.getPaymentStartTime() == null? null : Timestamp.valueOf(payment.getPaymentStartTime()));
            statement.setInt(4, payment.getStatus());
            statement.setDouble(5, payment.getAmount());
            statement.setString(6, payment.getTransactionId());
            statement.setString(7, payment.getDescription());
            statement.setLong(8, payment.getCardId());
            statement.setLong(9, payment.getUserId());
            statement.setLong(10, payment.getFoundationId());
            if (payment.getFundraiseId() == null) {
                statement.setObject(11, null);
            } else {
                statement.setLong(11, payment.getFundraiseId());
            }

            log.debug(CREATE_PAYMENT);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SauapProcessException("PAYMENT REP | Error while process create payment", ex);
        }
    }

    @Override
    public void updateStatus(String paymentId, PaymentStatus status, String errorMessage) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
            statement.setInt(1, status.getStatus());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(3, errorMessage);
            statement.setString(4, paymentId);

            log.debug(UPDATE_STATUS);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new SauapProcessException("PAYMENT REP | Error while process update payment status", ex);
        }
    }

    @Override
    public Payment findPaymentByPaymentId(String paymentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PAYMENTS_BY_ID)) {
            statement.setString(1, paymentId);
            ResultSet resultSet = statement.executeQuery(SELECT_PAYMENTS_BY_ID);
            if (resultSet.next()) {
                log.debug(SELECT_PAYMENTS_BY_ID);
                return (new PaymentMapper().mapRow(resultSet, 1));
            }
            return null;
        } catch (SQLException ex) {
            throw new SauapProcessException("PAYMENT REP | Error while process get payment", ex);
        }
    }
}
