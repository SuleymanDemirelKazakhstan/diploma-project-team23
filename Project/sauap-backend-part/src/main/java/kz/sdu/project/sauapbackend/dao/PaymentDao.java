package kz.sdu.project.sauapbackend.dao;

import kz.sdu.project.sauapbackend.dto.PaymentStatus;
import kz.sdu.project.sauapbackend.entity.Payment;

public interface PaymentDao {

    void create(Payment payment);

    void updateStatus(String paymentId, PaymentStatus status, String errorDescription);

    Payment findPaymentByPaymentId(String paymentId);

}
