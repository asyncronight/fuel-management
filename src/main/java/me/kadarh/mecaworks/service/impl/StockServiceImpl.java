package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.repo.others.StockRepo;
import me.kadarh.mecaworks.service.StockService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepo stockRepo;

    public StockServiceImpl(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    /**
     * @param stock to add
     * @return the Stock
     */
    @Override
    public Stock add(Stock stock) {
        log.info("Service= StockServiceImpl - calling methode add");
        try {
            return stockRepo.save(stock);
        } catch (Exception e) {
            log.debug("cannot add Stock , failed operation");
            throw new OperationFailedException("L'ajout du Stock a echouée ", e);
        }
    }

    @Override
    public Stock get(Long id) {
        log.info("Service-StockServiceImpl Calling getStock with params :" + id);
        try {
            return stockRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find Stock with id = :" + id);
            throw new ResourceNotFoundException("Stock introuvable", e);
        } catch (Exception e) {
            log.info("Problem , cannot get Stock with id = :" + id);
            throw new OperationFailedException("Problème lors de la recherche du Stock", e);
        }
    }

    @Override
    public Stock getLastStock(Chantier chantier) {
        log.info("Service-StockServiceImpl Calling getLastStock  :");
        try {
            return stockRepo.findLastStock(chantier.getId()).get();
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find last Stock");
            return null;
        } catch (Exception e) {
            log.info("Problem , cannot get last Stock");
            throw new OperationFailedException("Problème lors de la recherche du Stock", e);
        }
    }


}
