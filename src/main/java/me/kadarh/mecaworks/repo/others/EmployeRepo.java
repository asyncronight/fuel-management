package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeRepo extends JpaRepository<Employe, Long> {

    Optional<Employe> findByNom(String nom);
}
