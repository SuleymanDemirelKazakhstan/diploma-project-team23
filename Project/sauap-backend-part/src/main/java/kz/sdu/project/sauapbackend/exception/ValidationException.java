package kz.sdu.project.sauapbackend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {
    private String message;
    private String errorCode;

    public ValidationException(String message, ErrorCode errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode.getCode();
    }
}
