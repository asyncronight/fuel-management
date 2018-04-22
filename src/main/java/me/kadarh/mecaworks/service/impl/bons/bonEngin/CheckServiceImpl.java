package me.kadarh.mecaworks.service.impl.bons.bonEngin;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.others.ChantierRepo;
import me.kadarh.mecaworks.repo.others.EmployeRepo;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.service.AlerteService;
import me.kadarh.mecaworks.service.StockService;
import me.kadarh.mecaworks.service.bons.BonLivraisonService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;

/**
 * @author kadarH
 */

@Service
@Slf4j
@Transactional
public class CheckServiceImpl {

    private final BonEnginRepo bonEnginRepo;
    private final AlerteService alerteService;
    private final BonLivraisonService bonLivraisonService;
    private final StockService stockService;
    private final EnginRepo enginRepo;
    private final ChantierRepo chantierRepo;
    private final EmployeRepo employeRepo;

    public CheckServiceImpl(BonEnginRepo bonEnginRepo, AlerteService alerteService, BonLivraisonService bonLivraisonService, StockService stockService, EnginRepo enginRepo, ChantierRepo chantierRepo, EmployeRepo employeRepo) {
        this.bonEnginRepo = bonEnginRepo;
        this.alerteService = alerteService;
        this.bonLivraisonService = bonLivraisonService;
        this.stockService = stockService;
        this.enginRepo = enginRepo;
        this.chantierRepo = chantierRepo;
        this.employeRepo = employeRepo;
    }

    public boolean hasLogicQuantite(BonEngin bonEngin) {
        log.info("Service : Testing if Quantité Logic");
        return (bonEngin.getQuantite() < 2 * bonEngin.getEngin().getSousFamille().getCapaciteReservoir());
    }

    public boolean hasLogicCompteur(BonEngin bonEngin, BonEngin lastBonEngin) {
        log.info("Service : Testing if Compteur is Logic");
        long days;
        days = Period.between(lastBonEngin.getDate(), bonEngin.getDate()).getDays();
        if (days <= 0) days = 1;
        long diff, diff1;
        String typeCompteur = bonEngin.getEngin().getSousFamille().getTypeCompteur().name();
        if (typeCompteur.equals(TypeCompteur.H.name())) {
            return ((bonEngin.getCompteurAbsoluH() - lastBonEngin.getCompteurAbsoluH()) < days * 24);
        } else if (typeCompteur.equals(TypeCompteur.KM.name())) {
            return ((bonEngin.getCompteurAbsoluKm() - lastBonEngin.getCompteurAbsoluKm()) < days * 2200);
        } else if (typeCompteur.equals(TypeCompteur.KM_H.name())) {
            diff = bonEngin.getCompteurAbsoluH() - lastBonEngin.getCompteurAbsoluH();
            diff1 = bonEngin.getCompteurAbsoluKm() - lastBonEngin.getCompteurAbsoluKm();
            return (diff < days * 24) && (diff1 < days * 2200);
        }
        throw new OperationFailedException("operation echouée , typeCompteur introuvable");
    }

}
