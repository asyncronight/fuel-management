package me.kadarh.mecaworks.repo.alertes;

import me.kadarh.mecaworks.domain.alertes.AlerteLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteLivraisonRepo extends JpaRepository<AlerteLivraison, Long> {
}
