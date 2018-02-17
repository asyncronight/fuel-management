package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.alertes.AlerteEngin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteRepo extends JpaRepository<AlerteEngin, Long> {
}
