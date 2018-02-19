package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarqueRepo extends JpaRepository<Marque, Long> {

    Optional<Marque> findByNom(String name);
}
