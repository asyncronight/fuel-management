package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonEnginRepo extends JpaRepository<BonEngin, Long> {

    @Query(nativeQuery = true, value = "select * from BON_ENGIN  where ENGIN_ID=?1 order by DATE_BON desc limit 1 ")
    BonEngin findLastBonEngin(Long id);

    @Query(nativeQuery = true, value = "select * from BON_ENGIN  where ENGIN_ID=?1 and COMPTEUR_HEN_PANNE=false and PLEIN=true order by COMPTEUR_ABSOLUH desc limit 1")
    BonEngin findLastBonEnginH_toConsommation(Engin engin);

    @Query(nativeQuery = true, value = "select * from BON_ENGIN  where ENGIN_ID=?1 and COMPTEUR_KMEN_PANNE=false and PLEIN=true order by COMPTEUR_ABSOLU_KM desc limit 1")
    BonEngin findLastBonEnginKm_toConsommation(Engin engin);

    @Query(nativeQuery = true, value = "select * from BON_ENGIN where COMPTEUR_ABSOLUH > ?1")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_H(Long compteurAbsoluH);

    @Query(nativeQuery = true, value = "select * from BON_ENGIN where COMPTEUR_ABSOLU_KM > ?1")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_Km(Long compteurAbsoluKm);
}
