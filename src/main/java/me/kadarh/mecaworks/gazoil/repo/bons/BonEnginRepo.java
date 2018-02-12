package me.kadarh.mecaworks.gazoil.repo.bons;

import me.kadarh.mecaworks.gazoil.domain.bons.BonEngin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonEnginRepo extends JpaRepository<BonEngin, Long> {


}
