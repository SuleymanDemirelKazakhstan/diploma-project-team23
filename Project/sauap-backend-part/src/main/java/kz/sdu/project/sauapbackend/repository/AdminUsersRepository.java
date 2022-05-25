package kz.sdu.project.sauapbackend.repository;

import kz.sdu.project.sauapbackend.entity.AdminUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUsersRepository extends JpaRepository<AdminUsers, Long> {

    Optional<AdminUsers> findByUsername(String username);

}
