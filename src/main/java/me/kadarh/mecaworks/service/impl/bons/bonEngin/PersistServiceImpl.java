package me.kadarh.mecaworks.service.impl.bons.bonEngin;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.Alerte;
import me.kadarh.mecaworks.domain.alertes.TypeAlerte;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.service.AlerteService;
import me.kadarh.mecaworks.service.StockService;
import me.kadarh.mecaworks.service.bons.BonLivraisonService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Slf4j
@Transactional
public class PersistServiceImpl {

    private final AlerteService alerteService;
    private final BonLivraisonService bonLivraisonService;
    private final StockService stockService;
    private final BonEnginRepo bonEnginRepo;

    public PersistServiceImpl(AlerteService alerteService, BonLivraisonService bonLivraisonService, StockService stockService, BonEnginRepo bonEnginRepo) {
        this.alerteService = alerteService;
        this.bonLivraisonService = bonLivraisonService;
        this.stockService = stockService;
        this.bonEnginRepo = bonEnginRepo;
    }

    public void insertAlertes(BonEngin bonEngin) {
        BonEngin lastBonEngin = getLastBonEngin(bonEngin.getEngin());
        boolean okey = false;
        if (lastBonEngin != null) okey = true;
        // Verify if chauffeur is changed.
        if (lastBonEngin.getChauffeur() != bonEngin.getChauffeur() && okey)
            insertAlerte(bonEngin, "Chauffeur changé", TypeAlerte.CHAUFFEUR_CHANGED);
        // Verify if compteur is en panne , and if no verify if compteur last bon was en panne
        if (whichCompteurIsDown(bonEngin) == 1)
            insertAlerte(bonEngin, "Compteur H En Panne", TypeAlerte.COMPTEUR_H_EN_PANNE);
        else if (whichCompteurIsDown(lastBonEngin) == 1 && okey)
            insertAlerte(bonEngin, "Compteur H Reparé", TypeAlerte.COMPTEUR_H_REPARE);

        if (whichCompteurIsDown(bonEngin) == 2 && okey)
            insertAlerte(bonEngin, "Compteur Km En Panne", TypeAlerte.COMPTEUR_KM_EN_PANNE);
        else if (whichCompteurIsDown(lastBonEngin) == 2)
            insertAlerte(bonEngin, "Compteur Km Reparé", TypeAlerte.COMPTEUR_KM_REPARE);

        if (whichCompteurIsDown(bonEngin) == 3) {
            insertAlerte(bonEngin, "Compteur H En Panne", TypeAlerte.COMPTEUR_H_EN_PANNE);
            insertAlerte(bonEngin, "Compteur Km En Panne", TypeAlerte.COMPTEUR_KM_EN_PANNE);
        } else if (okey) {
            if (whichCompteurIsDown(lastBonEngin) == 1)
                insertAlerte(bonEngin, "Compteur H Reparé", TypeAlerte.COMPTEUR_H_REPARE);
            if (whichCompteurIsDown(lastBonEngin) == 2)
                insertAlerte(bonEngin, "Compteur Km Reparé", TypeAlerte.COMPTEUR_KM_REPARE);
        }

        //verify quantité
        if (bonEngin.getQuantite() > bonEngin.getEngin().getSousFamille().getCapaciteReservoir())
            insertAlerte(bonEngin, "Quantité de gazoil depasse la capacité de reservoir", TypeAlerte.QUANTITE_MORE_THAN_RESERVOIR);
    }

    public void insertAlerte(BonEngin bonEngin, String msg, TypeAlerte type) {
        Alerte alerte = new Alerte();
        alerte.setDate(LocalDate.now());
        alerte.setIdBon(bonEngin.getId());
        alerte.setMessage(msg);
        alerte.setEtat(true);
        alerte.setTypeAlerte(type);
        alerteService.add(alerte);
    }

    public void insertStock(BonEngin bonEngin) {
        Stock stock = new Stock();
        stock.setChantier(bonEngin.getChantierTravail());
        stock.setDate(bonEngin.getDate());
        stock.setSortieE(bonEngin.getQuantite());
        stockService.add(stock);
    }

    public void insertBonLivraison(BonEngin bonEngin) {
        BonLivraison bonLivraison = new BonLivraison();
        bonLivraison.setDate(bonEngin.getDate());
        bonLivraison.setChantierDepart(bonEngin.getChantierGazoil());
        bonLivraison.setChantierArrivee(bonEngin.getChantierTravail());
        // Todo  : ask soufiane
        bonLivraison.setCode(bonEngin.getCode() + "X" + bonEngin.getId());
        bonLivraison.setPompiste(bonEngin.getPompiste());
        bonLivraison.setTransporteur(bonEngin.getChauffeur());
        bonLivraison.setQuantite(bonEngin.getQuantite());
        bonLivraisonService.add(bonLivraison);
    }

    public int whichCompteurIsDown(BonEngin bonEngin) {
        boolean cmpHenPanne = bonEngin.getCompteurHenPanne();
        boolean cmpKmenPanne = bonEngin.getCompteurKmenPanne();
        if (cmpHenPanne && cmpKmenPanne)
            return 3;
        else if (cmpKmenPanne)
            return 2;
        else if (cmpHenPanne)
            return 1;
        else
            return 0;
    }

    public BonEngin getLastBonEngin(Engin engin) {
        try {
            return bonEnginRepo.findLastBonEngin(engin.getId());
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find last BonEngin with id = :" + engin.getId());
            throw new ResourceNotFoundException("BonEngin introuvable, c'est le premier bon de cette engin", e);
        } catch (Exception e) {
            log.info("Problem , cannot find last BonEngin with id = :" + engin.getId());
            throw new OperationFailedException("Operation echouée", e);
        }
    }
}
