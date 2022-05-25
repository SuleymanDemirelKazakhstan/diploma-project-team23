package kz.sdu.project.sauapbackend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SauapProcessException extends RuntimeException {
    private String errorMessage;

    public SauapProcessException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public SauapProcessException(String errorMessage, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
    }
}
