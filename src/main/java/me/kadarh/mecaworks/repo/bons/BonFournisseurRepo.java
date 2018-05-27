package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BonFournisseurRepo extends JpaRepository<BonFournisseur, Long> {


    @Query(nativeQuery = true, value = "select * from bon_fournisseur where DATE >= ?1 and DATE < ?2")
    List<BonFournisseur> findAllBetweenDates(LocalDate date1, LocalDate date2);

}
