package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 order by updated_at DESC limit 1")
    Optional<Stock> findLastStock(Long id_chantier);

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 and stock_reel <> 0 order by updated_at DESC limit 1")
    Optional<Stock> findLastStockReel(Long id_chantier);

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 and DATE >= ?2 and DATE < ?3")
    List<Stock> findAllByChantier(Long idc, LocalDate date1, LocalDate date2);

}
