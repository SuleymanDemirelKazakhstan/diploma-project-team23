package kz.sdu.project.sauapbackend.service;

import kz.sdu.project.sauapbackend.dto.MailMessage;

public interface MailService {

    void sendMessage(MailMessage message);

}
