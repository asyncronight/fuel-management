package me.kadarh.mecaworks.repo.alertes;

import me.kadarh.mecaworks.domain.alertes.AlerteFournissseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteFournisseurRepo extends JpaRepository<AlerteFournissseur, Long> {
}
