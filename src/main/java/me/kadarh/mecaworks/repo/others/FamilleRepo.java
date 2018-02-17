package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Famille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilleRepo extends JpaRepository<Famille, Long> {

	Optional<Famille> findByNom(String name);
}
