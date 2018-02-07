package me.kadarh.mecaworks.repo;

import me.kadarh.mecaworks.domain.Famille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilleRepo extends JpaRepository<Famille,Long> {
}
