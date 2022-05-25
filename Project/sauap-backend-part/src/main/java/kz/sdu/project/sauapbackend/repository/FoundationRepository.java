package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoundationRepository extends JpaRepository<Foundation, Long> {

    Optional<Foundation> findByFoundationIdAndIsApproved(Long id, Boolean isApproved);

}
