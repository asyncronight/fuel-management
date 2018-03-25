package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Chantier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChantierRepo extends JpaRepository<Chantier, Long> {

    Optional<Chantier> findByNom(String nom);
}
