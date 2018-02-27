package me.kadarh.mecaworks.repo.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Engin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonEnginRepo extends JpaRepository<BonEngin, Long> {

    @Query("select b from BonEngin b where b.engin=?1 ")
    Optional<BonEngin> findTheLastBonEngin(Engin engin);
}
