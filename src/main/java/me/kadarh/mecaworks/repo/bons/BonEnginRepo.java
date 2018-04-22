package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonEnginRepo extends JpaRepository<BonEngin, Long> {

    //todo : changing to Optional
    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 order by created_at desc limit 1")
    BonEngin findLastBonEngin(Long id);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_hen_panne=false and plein=true order by compteur_absoluh desc limit 1")
    BonEngin findLastBonEnginH_toConsommation(Long id);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_kmen_panne=false and plein=true order by compteur_absolu_km desc limit 1")
    BonEngin findLastBonEnginKm_toConsommation(Long id);

    @Query(nativeQuery = true, value = "select * from bon_engin where compteur_absoluh >= '?1'")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_H(Long compteurAbsoluH);

    @Query(nativeQuery = true, value = "select * from bon_engin where compteur_absolu_km >= '?1'")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_Km(Long compteurAbsoluKm);
}