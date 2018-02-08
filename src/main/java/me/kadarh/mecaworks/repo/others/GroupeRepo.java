package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepo extends JpaRepository<Groupe,Long> {
}
