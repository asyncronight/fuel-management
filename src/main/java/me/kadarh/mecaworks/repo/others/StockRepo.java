package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.domain.others.TypeBon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 order by date DESC , id DESC limit 1")
    Optional<Stock> findLastStock(Long id_chantier);

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 and date <=?2 order by date DESC , id DESC limit 1")
    Optional<Stock> findLastStockByDate(Long id_chantier, LocalDate date);

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 and stock_reel <> 0 order by id DESC limit 1")
    Optional<Stock> findLastStockReel(Long id_chantier);

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 and DATE >= ?2 and DATE < ?3")
    List<Stock> findAllByChantier(Long idc, LocalDate date1, LocalDate date2);

    @Query(nativeQuery = true, value = "select * from stock where type_bon =?1 and id_bon= ?2 order by id DESC limit 1")
    Optional<Stock> findByTypeBonAndId_Bon(TypeBon typeBon, Long idB);

    @Query(nativeQuery = true, value = "select * from stock where id_bon =?1 order by id")
    List<Stock> findAllById_Bon(Long id_bon);

    @Query(nativeQuery = true, value = "select * from stock where chantier_id =?1 and DATE > ?2 ORDER BY DATE ASC")
    List<Stock> findAllByChantierAfterStockReel(Long idc, LocalDate date);
}
