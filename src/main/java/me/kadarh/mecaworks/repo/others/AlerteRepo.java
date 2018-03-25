package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.alertes.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteRepo extends JpaRepository<Alerte, Long> {
}
