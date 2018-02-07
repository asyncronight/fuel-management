package me.kadarh.mecaworks.repo;

import me.kadarh.mecaworks.domain.Engin;
import me.kadarh.mecaworks.domain.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepo extends JpaRepository<Groupe,Long> {
}
