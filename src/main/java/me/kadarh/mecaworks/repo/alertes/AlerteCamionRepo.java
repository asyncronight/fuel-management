package me.kadarh.mecaworks.repo.alertes;

import me.kadarh.mecaworks.domain.alertes.AlerteCamion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteCamionRepo extends JpaRepository<AlerteCamion, Long> {
}
