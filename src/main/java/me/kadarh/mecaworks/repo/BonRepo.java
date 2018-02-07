package me.kadarh.mecaworks.repo;

import me.kadarh.mecaworks.domain.Bon;
import me.kadarh.mecaworks.domain.Chantier;
import me.kadarh.mecaworks.domain.Engin;
import me.kadarh.mecaworks.domain.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BonRepo extends JpaRepository<Bon,Long> {


    @Query("select b from Bon b where b.date between ?1 and ?2")
    List<Bon> findAllBetween(LocalDate date1,LocalDate date2);


    @Query("select b from Bon b where lower(b.engin) like lower(concat('%',?1 ,'%') )" +
            " and lower(b.chantier) like lower(concat('%',?2 ,'%') )" +
            " and lower(b.groupe) like lower(concat('%',?3 ,'%') )" +
            " and b.date between ?4 and ?5")
    List<Bon> findAllByBetween(Engin engin, Chantier chantier, Groupe groupe, LocalDate date1, LocalDate date2);

}
