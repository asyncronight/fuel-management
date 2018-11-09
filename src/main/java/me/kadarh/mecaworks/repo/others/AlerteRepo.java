package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.alertes.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlerteRepo extends JpaRepository<Alerte, Long> {

    List<Alerte> findByEtatOrderByCreatedAt(boolean etat);

    void deleteAllByBonEngin_Id(Long idBon);

    @Query(nativeQuery = true, value = "select * from alerte where bon_engin_id     = ?1")
    List<Alerte> findAllByIdBonEngin(Long id);
}
