package me.kadarh.mecaworks.repo;

import me.kadarh.mecaworks.domain.Alerte;
import me.kadarh.mecaworks.domain.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteRepo extends JpaRepository<Alerte,Long> {
}
