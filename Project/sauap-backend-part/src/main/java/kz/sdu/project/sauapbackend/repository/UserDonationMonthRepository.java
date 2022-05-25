package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.UserDonationMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDonationMonthRepository extends JpaRepository<UserDonationMonth, Long> {

    Optional<UserDonationMonth> findByUserIdAndFoundationId(Long userId, Long foundationId);

    List<UserDonationMonth> findAllByUserIdAndIsDoneFalse(Long userId);

    List<UserDonationMonth> findAllByDonateDayAndIsDoneFalse(Integer donateDay);

}
