package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.AlerteEngin;
import me.kadarh.mecaworks.domain.alertes.TypeAlerte;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.service.AlerteService;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.*;
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
    private final AlerteService alerteService;

    public BonEnginServiceImpl(BonEnginRepo bonEnginRepo, AlerteService alerteService) {
        this.bonEnginRepo = bonEnginRepo;
        this.alerteService = alerteService;
    }

    /* ------------------------------------------------------------------------------ */
    /* ------------ CRUD METHODES -------------------------------------------------- */
    /* ---------------------------------------------------------------------------- */

    @Override
    public BonEngin add(BonEngin bonEngin) {
        log.info("Service= BonEnginServiceImpl - calling methode add");
        try {
            insertAlertes(bonEngin);
            return bonEnginRepo.save(bonEngin);
        } catch (Exception e) {
            log.debug("cannot add bonEngin , failed operation");
            throw new OperationFailedException("L'ajout du bonEngin a echouée ", e);
        }
    }

    @Override
    public List<BonEngin> bonList(Long idEngin, LocalDate date1, LocalDate date2) {
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            bonEnginRepo.delete(bonEnginRepo.findById(id).get());
        } catch (Exception e) {
            throw new OperationFailedException("Probleme lors de la suppression du bon, ce bon ne peut pas être supprimer", e);
        }
    }

    @Override
    public Page<BonEngin> getPage(Pageable pageable, String search) {
        log.info("Service- employeServiceImpl Calling employeList with params :" + pageable + ", " + search);
        try {
            if (search.isEmpty()) {
                log.debug("fetching bonEngins page");
                return bonEnginRepo.findAll(pageable);
            } else {
                log.debug("Searching by :" + search);
                //creating example
                //Searching by nom pompiste and chauffeur , code engin , code bon
                BonEngin bonEngin = new BonEngin();
                Employe employe = new Employe();
                employe.setNom(search);
                Engin engin = new Engin();
                engin.setCode(search);
                bonEngin.setPompiste(employe);
                bonEngin.setChauffeur(employe);
                bonEngin.setCode(search);
                bonEngin.setEngin(engin);
                //creating matcher
                ExampleMatcher matcher = ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues();
                Example<BonEngin> example = Example.of(bonEngin, matcher);
                Pageable pageable1 = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, bonEngin.getDate().toString()));
                log.debug("getting search results");
                return bonEnginRepo.findAll(example, pageable1);
            }
        } catch (Exception e) {
            log.debug("Failed retrieving list of employes");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    /* ------------------------------------------------------------------------------ */
    /* ------------ TRAITEMENT WITHOUT CALLING REPO -------------------------------- */
    /* ---------------------------------------------------------------------------- */

    /**
     * @param bon the current BonEngin
     * @return boolean value
     * 0 if one of (quantity or meter) hasn't a logic value
     * 1 else ( if compteur and quantité are logic )
     */
    public boolean hasErrors(BonEngin bon) {
        BonEngin lastBonEngin = getLastBonEngin(bon.getEngin());
        return (hasLogicQuantite(bon) && hasLogicCompteur(bon, lastBonEngin));
    }

    /**
     * @param bon the current BonEngin
     * @return boolean value
     * 0 if one of (quantity or meter) hasn't a logic value
     * 1 else ( if compteur and quantité are logic )
     */
    public boolean hasErrorsAttention(BonEngin bon) {
        BonEngin bonEngin = getLastBonEngin(bon.getEngin());
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.H)) {
            return (bon.getCompteurH() < bonEngin.getCompteurH());
        }
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.KM)) {
            return (bon.getCompteurKm() < bonEngin.getCompteurKm());
        }
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.KM_H)) {
            return (bon.getCompteurH() < bonEngin.getCompteurH()) || (bon.getCompteurKm() < bonEngin.getCompteurKm());
        }

        return true;
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

    /* ------------------------------------------------------------------------------ */
    /* ------------ PRIVATE METHODES ----------------------------------------------- */
    /* ---------------------------------------------------------------------------- */

    //Todo @user kad : fill this methode
    private void insertAlertes(BonEngin bonEngin) {
        BonEngin lastBonEngin = getLastBonEngin(bonEngin.getEngin());
        // Verify if chauffeur is changed.
        if (chauffeurIsChanged(bonEngin, lastBonEngin))
            insertAlerteChauffeurChanged(bonEngin.getId());
        // Verify if compteur is en panne.
        // Verify if compteur is not en panne and the last bon has compteur en panne ( insert
        // alerte " compteur Réparé ".
        // Verify quantité > quantité reservoir.
        // if yes insert Alerte.
        if (whichCompteurIsDown(bonEngin) != 2)
            insertAlerteCompteurEnPanne(bonEngin.getId());//todo : implement Km & H
    }

    //Todo : insertAlerteCompteurEnPanne and insertAlerteChaffeurChanged  in one function

    /**
     * Will insert an alerte
     * Type : Compteur En Panne
     */
    private void insertAlerteCompteurEnPanne(Long idEngin) {
        AlerteEngin alerteEngin = new AlerteEngin();
        alerteEngin.setDate(LocalDate.now());
        alerteEngin.setIdBon(idEngin);
        alerteEngin.setMessage("the msg");//TODO : change the msg
        alerteEngin.setTypeAlerte(TypeAlerte.COMPTEUR_EN_PANNE);//par exemple ou safi
        alerteService.add(alerteEngin);
    }

    /**
     * Will insert an alerte
     * Type : Chauffeur_Changed
     */
    private void insertAlerteChauffeurChanged(Long idEngin) {
        AlerteEngin alerteEngin = new AlerteEngin();
        alerteEngin.setDate(LocalDate.now());
        alerteEngin.setIdBon(idEngin);
        alerteEngin.setMessage("the msg to set");//todo : change the msg
        alerteEngin.setTypeAlerte(TypeAlerte.CHAUFFEUR_CHANGED);
        alerteService.add(alerteEngin);
    }

    /**
     * @param bonEngin the current bon
     * @return boolean value
     * 0 if quantity isn't logic
     * 1 else
     */
    private boolean hasLogicQuantite(BonEngin bonEngin) {
        log.info("Service : Testing if Quantité Logic");
        return bonEngin.getQuantite() < 2 * bonEngin.getEngin().getSousFamille().getCapaciteReservoir();
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

    private Float calculConsommationMax(BonEngin bonEngin) {

        return 3f;
    }

    /**
     * @param engin the Engin of the bon
     * @return Bon Engin
     * The last bonEngin having the same Engin
     */
    private BonEngin getLastBonEngin(Engin engin) {
        try {
            return bonEnginRepo.findLastBonEngin(engin);
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find last BonEngin with id = :" + engin.getId());
            throw new ResourceNotFoundException("BonEngin introuvable, c'est le premier bon de cette engin", e);
        } catch (Exception e) {
            log.info("Problem , cannot find last BonEngin with id = :" + engin.getId());
            throw new OperationFailedException("Operation echouée", e);
        }
    }

}

