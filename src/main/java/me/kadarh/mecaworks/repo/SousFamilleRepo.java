package me.kadarh.mecaworks.repo;

import me.kadarh.mecaworks.domain.SousFamille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SousFamilleRepo extends JpaRepository<SousFamille,Long> {
}
