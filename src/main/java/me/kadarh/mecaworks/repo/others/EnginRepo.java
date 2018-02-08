package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnginRepo extends JpaRepository<Engin,Long> {
}
