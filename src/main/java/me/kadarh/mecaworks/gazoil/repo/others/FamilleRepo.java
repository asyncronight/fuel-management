package me.kadarh.mecaworks.gazoil.repo.others;

import me.kadarh.mecaworks.gazoil.domain.others.Famille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilleRepo extends JpaRepository<Famille,Long> {


    Optional<Famille> findByNom(String name);
}
