package me.kadarh.mecaworks.repo;

import me.kadarh.mecaworks.domain.Chantier;
import me.kadarh.mecaworks.domain.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChantierRepo extends JpaRepository<Chantier,Long> {

    Chantier findByNom(String nom);
}
