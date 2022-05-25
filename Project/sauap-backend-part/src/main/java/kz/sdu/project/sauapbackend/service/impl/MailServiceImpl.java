package kz.sdu.project.sauapbackend.service.impl;

import kz.sdu.project.sauapbackend.dto.MailMessage;
import kz.sdu.project.sauapbackend.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
@Slf4j
@Primary
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(MailMessage message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setSubject(message.getMailSubject());
            mimeMessage.setFrom(new InternetAddress(message.getMailFrom(), message.getPersonal()));
            mimeMessage.setRecipients(Message.RecipientType.TO, message.getMailTo());
            mimeMessage.setContent(message.getMailContent(), message.getContentType());
            mimeMessage.saveChanges();

            mailSender.send(mimeMessage);
            log.info("MAIL SERVICE | Mail send successfully | sendTime: {}, body: {}", LocalDateTime.now(), message.toStringMail());
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("MAIL SERVICE | Error while send message | sendTime: {}, body: {}", LocalDateTime.now(), message.toStringMail(), e);
        }
    }
}
