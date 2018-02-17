package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonFournisseurRepo extends JpaRepository<BonFournisseur, Long> {


}
