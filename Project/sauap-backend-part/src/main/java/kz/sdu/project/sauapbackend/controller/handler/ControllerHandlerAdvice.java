package kz.sdu.project.sauapbackend.controller.handler;

import kz.sdu.project.sauapbackend.SauapAdminUser;
import kz.sdu.project.sauapbackend.dto.ErrorDto;
import kz.sdu.project.sauapbackend.exception.SauapProcessException;
import kz.sdu.project.sauapbackend.exception.ErrorCode;
import kz.sdu.project.sauapbackend.exception.ValidationException;
import kz.sdu.project.sauapbackend.service.impl.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ControllerHandlerAdvice extends ResponseEntityExceptionHandler {

    private SecurityService securityService;

    @Autowired
    public ControllerHandlerAdvice(SecurityService securityService) {
        this.securityService = securityService;
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleServiceException(Exception ex, WebRequest request) {
        SauapAdminUser adminUser = securityService.getCurrentUser();
        String errorCode;
        String errorMessage;
        int statusCode;
        if (ex instanceof ValidationException) {
            errorCode = ((ValidationException) ex).getErrorCode();
            errorMessage = ((ValidationException) ex).getMessage();
            statusCode = HttpStatus.BAD_REQUEST.value();
        } else if (ex instanceof SauapProcessException) {
            errorCode = "-1";
            errorMessage = ((SauapProcessException) ex).getMessage();
            statusCode = HttpStatus.NOT_FOUND.value();
        } else {
            errorCode = ErrorCode.SERVICE_ERROR.getCode();
            errorMessage = "unexpected internal service error";
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            log.error("USER_ID: {} | USERNAME: {} | GENERAL EXCEPTION OCCURRED: {}",
                    adminUser.getUserId(), adminUser.getUsername(), ex.getMessage());
        }

        ErrorDto errorDto = new ErrorDto(errorCode, errorMessage);
        return super.handleExceptionInternal(ex, errorDto, new HttpHeaders(), HttpStatus.valueOf(statusCode), request);
    }
}
