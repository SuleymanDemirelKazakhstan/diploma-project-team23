package kz.sdu.project.sauapbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class MailMessage {

    private String mailFrom;

    private String mailTo;

    private String personal;

    private String mailCc;

    private String mailBcc;

    private String mailSubject;

    private String mailContent;

    private String contentType;

    private List<Object> attachments;

    public MailMessage() {
        contentType = "text/plain";
    }

    public String toStringMail() {
        return "MailMessage(" +
                "mailFrom=" + mailFrom + ", " +
                "mailTo=" + mailTo + ", " +
                "subject=" + mailSubject + ", " +
                "contentType=" + contentType + ", " +
                "personal=" + personal + ", " +
                "mailCc=" + mailCc + ", " +
                "mailBcc=" + mailBcc + ";";
    }
}
