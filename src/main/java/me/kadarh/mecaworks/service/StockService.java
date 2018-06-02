package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Stock;

public interface StockService {

    Stock add(Stock stock);

    Stock get(Long id);

    Stock getLastStock(Chantier chantier);
}
