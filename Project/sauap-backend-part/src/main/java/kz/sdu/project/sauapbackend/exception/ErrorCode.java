package kz.sdu.project.sauapbackend.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FIELD_IS_INVALID("-1009"),
    FIELD_FORMAT_INVALID("-1010"),
    USER_ALREADY_EXIST("-1011"),
    PASSWORD_IS_NOT_CORRECT("-1012"),
    USER_NOT_FOUND("-1013"),
    FOUNDATION_NOT_FOUND("-1014"),
    CARD_IS_ALREADY_EXIST("-1015"),
    CARD_IS_NOT_FOUND("-1016"),
    FUNDRAISE_NOT_FOUND("-1017"),
    DONATE_GOOD_NOT_FOUND("-1018"),
    SERVICE_ERROR("-1000");

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }
}
