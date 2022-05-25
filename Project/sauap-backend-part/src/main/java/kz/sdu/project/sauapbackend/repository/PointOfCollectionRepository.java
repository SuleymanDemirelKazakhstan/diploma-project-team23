package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.PointOfCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointOfCollectionRepository extends JpaRepository<PointOfCollection, Long> {

    List<PointOfCollection> findAllByCityAndIsActiveTrue(String city);

    List<PointOfCollection> findAllByCityAndTypeAndIsActiveTrue(String city, String type);

}
