package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonCamion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonCamionRepo extends JpaRepository<BonCamion, Long> {


}
