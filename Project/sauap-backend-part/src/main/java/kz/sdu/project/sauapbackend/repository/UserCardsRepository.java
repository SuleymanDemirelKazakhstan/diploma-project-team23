package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.UserCards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCardsRepository extends JpaRepository<UserCards, Long> {

    List<UserCards> findAllByUserId(Long userId);

    Optional<UserCards> findByCardNumberAndCardHolderName(String cardNumber, String cardHolder);

    Optional<UserCards> findByCardIdAndUserId(Long cardId, Long userId);

}
