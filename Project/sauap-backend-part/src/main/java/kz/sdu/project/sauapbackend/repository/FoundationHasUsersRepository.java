package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.FoundationHasUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoundationHasUsersRepository extends JpaRepository<FoundationHasUsers, Long> {

    List<FoundationHasUsers> findAllByFoundationId(Long foundationId);

    List<FoundationHasUsers> findFoundationHasUsersByUserIdAndRole(Long userId, String role);

}
