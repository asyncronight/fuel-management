package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.SousFamille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SousFamilleRepo extends JpaRepository<SousFamille,Long> {
}
