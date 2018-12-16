package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BonEnginRepo extends JpaRepository<BonEngin, Long> {

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and DATE_BON <= ?2 order by DATE_BON desc limit 1")
    BonEngin findLastBonEngin(Long id,LocalDate localDate);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 order by DATE_BON desc limit 1")
    BonEngin findLastBonEngin(Long id);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_absoluh>?2" +
            " order by compteur_absoluh asc")
    List<BonEngin> findListBonEnginH_toConsommation(Long id,Long cmpAbsH);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_absolukm>?2" +
            " order by compteur_absoluKm asc")
    List<BonEngin> findListBonEnginKm_toConsommation(Long id,Long cmpAbsKm);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_hen_panne=false and plein=true " +
            " and DATE_BON <= ?2 order by compteur_absoluh desc limit 1")
    BonEngin findLastBonEnginH_toConsommation(Long id,LocalDate dateBon);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_kmen_panne=false and plein=true " +
            " and DATE_BON <= ?2 order by compteur_absoluh desc limit 1")
    BonEngin findLastBonEnginKm_toConsommation(Long id,LocalDate dateBon);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id=?1 and compteur_absoluh>=?2")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_H(Long engin_id, Long compteur_absoluh);

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id =?1 and compteur_absolu_km >= ?2")
    List<BonEngin> findAllBetweenLastBonAndCurrentBon_Km(Long id, Long compteurAbsoluKm);

    @Query(nativeQuery = true, value = "select * from bon_engin where DATE_BON >= ?1 and DATE_BON < ?2")
    List<BonEngin> findAllBetweenDates(LocalDate date1, LocalDate date2);

    @Query(nativeQuery = true, value = "select * from bon_engin where chantier_travail_id =?1 and DATE_BON >= ?2 and DATE_BON < ?3")
    List<BonEngin> findAllByChantier(Long idc, LocalDate date1, LocalDate date2);

    @Query(nativeQuery = true, value = "select * from bon_engin group by engin_id")
    List<BonEngin> findAllGroupByEngin();

    @Query(nativeQuery = true, value = "select * from bon_engin where engin_id = ?1")
    List<BonEngin> findAllByEnginId(Long id);
}