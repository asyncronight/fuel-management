package me.kadarh.mecaworks.repo.user;

import me.kadarh.mecaworks.domain.user.ChantierBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChantierBatchRepo extends JpaRepository<ChantierBatch, Long> {

    List<ChantierBatch> findAllByMoisAndAnnee(int mois, int Annee);
}
