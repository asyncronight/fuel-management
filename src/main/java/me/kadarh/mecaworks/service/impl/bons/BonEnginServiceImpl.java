package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
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

    private boolean hasLogicQuantite(BonEngin bonEngin) {
        return bonEngin.getQuantite() < 2 * bonEngin.getEngin().getSousFamille().getCapaciteReservoir();
    }

    /**
     * @param bonEngin
     * @return boolean value
     * 0 if meter is not logic ( compteur illogique )
     * 1 else
     */
    private boolean hasLogicCompteurH(BonEngin bonEngin, BonEngin lastBonEngin) {
        // kanakhod akhir bon engin  ( LastBonEngin )
        // kanakhod la date dialou + la date dial had jdid
        // kanhseb le nombre de jours
        // kanakhod le compteur absolu dial LastBonEngin
        // kanakhod le compteur absolu dial had le nouveau engin
        // kandir la difference binathom : diff
        // nverifie que diff > nbr_jours * 24

        long days = Period.between(lastBonEngin.getDate(), bonEngin.getDate()).getDays();
        if (days == 0) days = 1;
        long diff = bonEngin.getCompteurAbsoluH() - lastBonEngin.getCompteurAbsoluH();
        return (diff < days * 24);
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
        boolean cmpHenPanne = true;// bonEngin.getCompteurHenPanne();
        boolean cmpKmenPanne = true;// bonEngin.getCompteurKmenPanne();
        if (cmpHenPanne && cmpKmenPanne)
            return 3;
        else if (cmpKmenPanne)
            return 2;
        else if (cmpHenPanne)
            return 1;
        else
            return 0;
    }

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

