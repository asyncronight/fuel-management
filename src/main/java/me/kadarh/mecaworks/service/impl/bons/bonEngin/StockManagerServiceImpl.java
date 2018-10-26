package me.kadarh.mecaworks.service.impl.bons.bonEngin;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.domain.others.TypeBon;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.others.StockRepo;
import me.kadarh.mecaworks.service.ChantierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 27/09/18
 */

@Service
@Transactional
@Slf4j
public class StockManagerServiceImpl {

    private final StockRepo stockRepo;
    private final ChantierService chantierService;
    private final BonEnginRepo bonEnginRepo;

    private BetweenStockAndBonLivraisonService betweenService;

    public StockManagerServiceImpl(StockRepo stockRepo, ChantierService chantierService, BonEnginRepo bonEnginRepo) {
        this.stockRepo = stockRepo;
        this.chantierService = chantierService;
        this.bonEnginRepo = bonEnginRepo;
    }

    @Autowired
    public void setBetweenService(BetweenStockAndBonLivraisonService betweenService) {
        this.betweenService = betweenService;
    }

    public void deleteStock(Long idC_gasoil, Long idC_travail, Long id_bon, TypeBon type_bon) {
        //verify date stock and date dernier inventaire
        //if date_stock > date_dernier_inventaire
        //then doUpdateStocks + doUpdateChantierStock
        //else don't do anything
        //always do : delete stock with id bon = id_bon and type bon = type_bon
        Optional<Stock> stock = stockRepo.findByTypeBonAndId_Bon(type_bon, id_bon);
        List<Stock> list = stockRepo.findAllById_Bon(id_bon);
        stockRepo.deleteAll(list);
        stock.ifPresent(stock1 -> update(idC_travail, idC_gasoil, stock1, type_bon, false));
        if (!stock.isPresent())
            updateStockChantier(idC_travail, list.get(0) != null ? list.get(0).getQuantite() : 0, false);
    }


    public void addStockUpdate(Long idC_travail, Long idC_gasoil, Stock stock, TypeBon type_bon) {
        update(idC_travail, idC_gasoil, stock, type_bon, true);
    }

    private void update(Long idC_travail, Long idC_gasoil, Stock stock, TypeBon type_bon, boolean signe) {
        if (type_bon.equals(TypeBon.BE)) {
            if (!idC_gasoil.equals(idC_travail)) {
                if (!signe) insertLivraison(stock.getId_Bon());
                if (weHaveToDoMiseAjour(stock.getDate(), idC_gasoil))
                    doMiseAjour(idC_gasoil, stock);
                if (weHaveToDoMiseAjour(stock.getDate(), idC_travail)) {
                    doMiseAjour(idC_travail, stock);
                    updateStockChantier(idC_travail, stock.getQuantite(), !signe);
                }
            } else {
                if (weHaveToDoMiseAjour(stock.getDate(), idC_travail)) {
                    doMiseAjour(idC_travail, stock);
                    updateStockChantier(idC_travail, stock.getQuantite(), !signe);
                }
            }
        }
        if (type_bon.equals(TypeBon.BF) && weHaveToDoMiseAjour(stock.getDate(), idC_travail)) {
            doMiseAjour(idC_travail, stock);
            updateStockChantier(idC_travail, stock.getQuantite(), signe);
        }
        if (type_bon.equals(TypeBon.BL)) {
            if (weHaveToDoMiseAjour(stock.getDate(), idC_gasoil)) {
                doMiseAjour(idC_gasoil, stock);
                updateStockChantier(idC_gasoil, stock.getQuantite(), !signe);
            }
            if (weHaveToDoMiseAjour(stock.getDate(), idC_travail)) {
                doMiseAjour(idC_travail, stock);
                updateStockChantier(idC_travail, stock.getQuantite(), signe);
            }
        }
    }

    private void insertLivraison(Long id_bon) {
        BonEngin bonEngin = bonEnginRepo.getOne(id_bon);
        bonEngin.setQuantite((-1) * bonEngin.getQuantite());
        betweenService.insertBonLivraison(bonEngin);
    }

    private boolean weHaveToDoMiseAjour(LocalDate dateStock, Long idC) {
        //get date of dernier mise a jour
        //compare them
        Optional<Stock> stock = stockRepo.findLastStockReel(idC);
        return stock.isPresent() && stock.get().getDate().isBefore(dateStock);
    }

    private void doMiseAjour(Long idc, Stock stock) {
        List<Stock> list = stockRepo.findAllByChantierAfterStockReel(idc, stockRepo.findLastStockReel(stock.getChantier().getId()).get().getDate());
        //Au debut ce stock = stock reel
        Stock stockInitial = stockRepo.findLastStockReel(idc).get();
        list.remove(stockInitial);

        Stock stock2;
        // On boucle, pour chaque stock on remplace le
        for (int i = 0; i < list.size(); i++) {
            stock2 = stockRepo.getOne(list.get(i).getId());
            if (stock.getTypeBon().equals(TypeBon.BE))
                stock2.setStockC(stockInitial.getStockC() - list.get(i).getQuantite());
            if (stock.getTypeBon().equals(TypeBon.BF))
                stock2.setStockC(stockInitial.getStockC() + list.get(i).getQuantite());
            if (stock.getTypeBon().equals(TypeBon.BL)) {
                if (stock.getSortieL() != 0 && stock.getSortieL() != null)
                    stock2.setStockC(stockInitial.getStockC() + list.get(i).getQuantite());
                if (stock.getEntreeL() != 0 && stock.getEntreeL() != null)
                    stock2.setStockC(stockInitial.getStockC() + list.get(i).getQuantite());
            }
            list.set(i, stock2);
            stockInitial = list.get(i);
        }
        stockRepo.saveAll(list);
    }

    private void updateStockChantier(Long idc, Integer quantite, boolean signe) {
        Chantier chantier = chantierService.get(idc);
        chantier.setStock(signe ? chantier.getStock() + quantite : chantier.getStock() - quantite);
        chantierService.update(chantier);
    }

}
