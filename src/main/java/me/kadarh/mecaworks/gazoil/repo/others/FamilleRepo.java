package me.kadarh.mecaworks.gazoil.repo.others;

import me.kadarh.mecaworks.gazoil.domain.others.Famille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilleRepo extends JpaRepository<Famille,Long> {
}
