package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonEnginRepo extends JpaRepository<BonEngin, Long> {

    @Query("select b from BonEngin b where b.engin=?1 order by b.date desc ")
    BonEngin findLastBonEngin(Engin engin);

    @Query("select b from BonEngin b where b.engin=?1 and b.compteurHenPanne=false and b.plein=true order by b.compteurAbsoluH desc ")
    BonEngin findLastBonEnginH_toConsommation(Engin engin);

    @Query("select b from BonEngin b where b.engin=?1 and b.compteurKmenPanne=false and b.plein=true order by b.compteurAbsoluKm desc")
    BonEngin findLastBonEnginKm_toConsommation(Engin engin);

    @Query("select b from BonEngin b where b.compteurAbsoluH > ?1")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_H(Long compteurAbsoluH);

    @Query("select b from BonEngin b where b.compteurAbsoluKm > ?1")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_Km(Long compteurAbsoluKm);
}
