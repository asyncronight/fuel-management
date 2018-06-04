package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BonLivraisonRepo extends JpaRepository<BonLivraison, Long> {

    @Query(nativeQuery = true, value = "select * from bon_livraison where DATE >= ?1 and DATE < ?2")
    List<BonLivraison> findAllBetweenDates(LocalDate date1, LocalDate date2);

}
