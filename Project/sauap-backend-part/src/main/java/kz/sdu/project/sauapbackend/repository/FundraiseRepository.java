package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.Fundraising;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundraiseRepository extends JpaRepository<Fundraising, Long> {
    List<Fundraising> findAllByOrderByIdDesc();

    List<Fundraising> findAllByFoundationIdOrderByIdDesc(Long foundationId);
}
