package kz.sdu.project.sauapbackend.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {
    private String paymentId;
    private String typeOfPayment;
    private LocalDateTime paymentStartTime;
    private Integer status;
    private Double amount;
    private String transactionId;
    private String description;
    private Long cardId;
    private Long userId;
    private Long foundationId;
    private Long fundraiseId;
    private LocalDateTime paymentLastUpdateTime;
    private String errorMessage;

    public static final String TABLE                = "payments";
    public static final String PAYMENT_ID           = "payment_id";
    public static final String TYPE_OF_PAYMENT      = "type_of_payment";
    public static final String PAYMENT_START_TIME   = "payment_start_time";
    public static final String STATUS               = "status";
    public static final String AMOUNT               = "amount";
    public static final String TRANSACTION_ID       = "transaction_id";
    public static final String DESCRIPTION          = "description";
    public static final String CARD_ID              = "card_id";
    public static final String USER_ID              = "user_id";
    public static final String FOUNDATION_ID        = "foundation_id";
    public static final String FUNDRAISE_ID         = "fundraise_id";
    public static final String PAYMENT_DONE_TIME    = "payment_last_update_time";
    public static final String ERROR_MESSAGE        = "error_message";

}
