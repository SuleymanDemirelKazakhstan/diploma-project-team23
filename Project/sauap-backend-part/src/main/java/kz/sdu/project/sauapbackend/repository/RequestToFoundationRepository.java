package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.RequestToFoundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestToFoundationRepository extends JpaRepository<RequestToFoundation, String> {

    List<RequestToFoundation> findAllByFoundationId(Long foundationId);

}
