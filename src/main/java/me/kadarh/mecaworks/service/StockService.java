package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {

    Stock add(Stock stock);

    Page<Stock> stockList(Pageable pageable, String search);

    Stock get(Long id);

    Stock getLastStock();
}
