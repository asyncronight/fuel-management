package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Slf4j
@Transactional
public class BonEnginServiceImpl implements BonEnginService {

    private final BonEnginRepo bonEnginRepo;

    public BonEnginServiceImpl(BonEnginRepo bonEnginRepo) {
        this.bonEnginRepo = bonEnginRepo;
    }

    @Override
    public BonEngin add(BonEngin bonEngin) {
        return null;
    }

    @Override
    public List<BonEngin> bonList(Long idEngin, LocalDate date1, LocalDate date2) {
        return null;
    }

    /**
     * @param bonEngin the current bon
     * @return 0 if quantity isn't logic
     * 1 else
     */
    private boolean hasLogicQuantite(BonEngin bonEngin) {
        log.info("Service : Testing if Quantité Logic");
        return bonEngin.getQuantite() < 2 * bonEngin.getEngin().getSousFamille().getCapaciteReservoir();
    }

    /**
     * @param bonEngin the current bon
     * @return boolean value
     * 0 if the bon contains one or many technical error
     * Example of technical errors :
     * - Quantity not logic
     * - Compteur not logic
     * -
     * 1 else
     */
    public boolean containTechnicalErrors(BonEngin bonEngin) {
        BonEngin lastBonEngin = getLastBonEngin(bonEngin.getEngin());
        if (!hasLogicQuantite(bonEngin)) {
            return false;
        }
        if (!hasLogicCompteur(bonEngin, lastBonEngin)) {
            return true;
        }
        return true;
    }

    public Map<String, String> getErrors(BonEngin bonEngin) {
        Map<String, String> map = new HashMap<>();
        if (!hasLogicQuantite(bonEngin)) {
            map.put("quantite", "La quantité que vous avez entré n'est pas logique");
        }
        return map;
    }

    /**
     * @param bonEngin the current bon
     * @param lastBonEngin the last bon
     * @return boolean value
     * 0 if meter is not logic ( compteur H illogique )
     * 1 else
     */
    private boolean hasLogicCompteur(BonEngin bonEngin, BonEngin lastBonEngin) {
        log.info("Service : Testing if Compteur is Logic");
        long days = Period.between(lastBonEngin.getDate(), bonEngin.getDate()).getDays();
        if (days == 0) days = 1;
        long diff, diff1;
        String typeCompteur = bonEngin.getEngin().getSousFamille().getTypeCompteur().name();
        if (typeCompteur.equals(TypeCompteur.H.name())) {
            diff = bonEngin.getCompteurAbsoluH() - lastBonEngin.getCompteurAbsoluH();
            return (diff < days * 24);
        } else if (typeCompteur.equals(TypeCompteur.KM.name())) {
            diff = bonEngin.getCompteurAbsoluKm() - lastBonEngin.getCompteurAbsoluKm();
            return (diff < days * 2200);
        } else if (typeCompteur.equals(TypeCompteur.KM_H.name())) {
            diff = bonEngin.getCompteurAbsoluH() - lastBonEngin.getCompteurAbsoluH();
            diff1 = bonEngin.getCompteurAbsoluKm() - lastBonEngin.getCompteurAbsoluKm();
            return (diff < days * 24) && (diff1 < days * 2200);
        }
        throw new OperationFailedException("operation echouée , typeCompteur introuvable");
    }

    /**
     * @param bonEngin
     * @return int value
     * 0 if any meter is down ( 2 compteurs en marche )
     * 1 if the hour meter is down (just compteur H is en panne)
     * 2 if the Km meter is down (just compteur Km is en panne)
     * 3 if all meters are down
     */
    private int whichCompteurIsDown(BonEngin bonEngin) {
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

    /**
     * @param bonEngin the current bon
     * @param lastBonEngin the last bon
     * @return
     * 0 if driver is changed
     * 1 else
     */
    private boolean chauffeurIsChanged(BonEngin bonEngin, BonEngin lastBonEngin) {
        return lastBonEngin.getChauffeur() != bonEngin.getChauffeur();
    }

    private boolean hasQuantiteLessThanReservoir(BonEngin bonEngin) {
        return bonEngin.getQuantite() < bonEngin.getEngin().getSousFamille().getCapaciteReservoir();
    }

    private BonEngin getLastBonEngin(Engin engin) {
        try {
            return bonEnginRepo.findTheLastBonEngin(engin).get();
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find last BonEngin with id = :" + engin.getId());
            throw new ResourceNotFoundException("BonEngin introuvable, c'est le premier bon de cette engin", e);
        } catch (Exception e) {
            log.info("Problem , cannot find last BonEngin with id = :" + engin.getId());
            throw new OperationFailedException("Operation echouée", e);
        }
    }

}

