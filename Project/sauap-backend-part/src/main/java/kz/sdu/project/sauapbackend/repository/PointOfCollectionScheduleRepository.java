package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.PointOfCollectionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointOfCollectionScheduleRepository extends JpaRepository<PointOfCollectionSchedule, Long> {

    Optional<PointOfCollectionSchedule> findAllByCollectionId(Long collectionId);

}
