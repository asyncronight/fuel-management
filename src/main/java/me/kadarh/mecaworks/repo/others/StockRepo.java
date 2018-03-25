package me.kadarh.mecaworks.repo.others;

import me.kadarh.mecaworks.domain.others.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

    @Query("select s from Stock s order by s.updatedAt desc")
    Stock findLastStock();
}
