package kz.sdu.project.sauapbackend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "user_cards")
public class UserCards extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;
    @Column(name = "card_holder_name", nullable = false, length = 100)
    private String cardHolderName;
    @Column(name = "card_number", nullable = false, length = 30)
    private String cardNumber;
    @Column(name = "card_valid_period", nullable = false, length = 10)
    private String cardValidPeriod;
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}

