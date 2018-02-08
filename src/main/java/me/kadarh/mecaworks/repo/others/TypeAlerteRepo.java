package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.TypeAlerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAlerteRepo extends JpaRepository<TypeAlerte,Long> {
}
