package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.PaymentResultDto;
import kz.sdu.project.sauapbackend.exception.CreateMessageException;
import kz.sdu.project.sauapbackend.exception.SauapProcessException;

public interface PaymentService<T> {

    PaymentResultDto makePayment(T payment) throws CreateMessageException, SauapProcessException;

}
