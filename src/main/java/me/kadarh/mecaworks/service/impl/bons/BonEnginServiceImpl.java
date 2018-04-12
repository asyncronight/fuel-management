package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.Alerte;
import me.kadarh.mecaworks.domain.alertes.TypeAlerte;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.others.ChantierRepo;
import me.kadarh.mecaworks.repo.others.EmployeRepo;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.service.AlerteService;
import me.kadarh.mecaworks.service.StockService;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import me.kadarh.mecaworks.service.bons.BonLivraisonService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final BonLivraisonService bonLivraisonService;
    private final StockService stockService;
    private final EnginRepo enginRepo;
    private final ChantierRepo chantierRepo;
    private final EmployeRepo employeRepo;

    public BonEnginServiceImpl(BonEnginRepo bonEnginRepo, AlerteService alerteService, BonLivraisonService bonLivraisonService, StockService stockService, EnginRepo enginRepo, ChantierRepo chantierRepo, EmployeRepo employeRepo) {
        this.bonEnginRepo = bonEnginRepo;
        this.alerteService = alerteService;
        this.bonLivraisonService = bonLivraisonService;
        this.stockService = stockService;
        this.enginRepo = enginRepo;
        this.chantierRepo = chantierRepo;
        this.employeRepo = employeRepo;
    }
    /* ------------------------------------------------------------------------------ */
    /* ------------ CRUD METHODES -------------------------------------------------- */
    /* ---------------------------------------------------------------------------- */

    @Override
    public BonEngin add(BonEngin bonEngin) {
        log.info("Service= BonEnginServiceImpl - calling methode add");
        try {
            if (bonEngin.getPlein())
                bonEngin = calculConsommation(bonEngin);
            if (bonEngin.getChantierGazoil() != bonEngin.getChantierTravail())
                insertBonLivraison(bonEngin);
            bonEngin = bonEnginRepo.save(bonEngin);
            insertAlertes(bonEngin);
            insertStock(bonEngin);

            return bonEngin;
        } catch (Exception e) {
            log.debug("cannot add bonEngin , failed operation");
            throw new OperationFailedException("L'ajout du bonEngin a echouée ", e);
        }
    }

    private void calculCompteursAbsolu(BonEngin bonEngin) {
        log.info("--- > Calcul compteur Absolu");
        String typeCompteur = bonEngin.getEngin().getSousFamille().getTypeCompteur().name();
        BonEngin bonEngin1 = getLastBonEngin(bonEngin.getEngin());
        if (typeCompteur.equals(TypeCompteur.H.name())) {
            if (bonEngin1 != null)
                bonEngin.setCompteurAbsoluH(bonEngin1.getCompteurAbsoluH() + bonEngin.getCompteurH());
            else
                bonEngin.setCompteurAbsoluH(bonEngin.getCompteurH());
        } else if (typeCompteur.equals(TypeCompteur.KM.name())) {
            if (bonEngin1 != null)
                bonEngin.setCompteurAbsoluKm(bonEngin1.getCompteurAbsoluKm() + bonEngin.getCompteurKm());
            else
                bonEngin.setCompteurAbsoluKm(bonEngin.getCompteurKm());
        } else if (typeCompteur.equals(TypeCompteur.KM_H.name())) {
            if (bonEngin1 != null) {
                bonEngin.setCompteurAbsoluH(bonEngin1.getCompteurAbsoluH() + bonEngin.getCompteurH());
                bonEngin.setCompteurAbsoluKm(bonEngin1.getCompteurAbsoluKm() + bonEngin.getCompteurKm());
            } else {
                bonEngin.setCompteurAbsoluH(bonEngin.getCompteurH());
                bonEngin.setCompteurAbsoluKm(bonEngin.getCompteurKm());
            }
        }
        log.info("--- > Calcul compteur AbsoluKm = " + bonEngin.getCompteurKm());
        log.info("--- > Calcul compteur AbsoluH = " + bonEngin.getCompteurH());
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
        log.info("Service- BonEnginServiceImpl Calling bonEnginList with params :" + pageable + ", " + search);
        try {
            if (search.isEmpty()) {
                log.debug("fetching bonEngins page");
                return bonEnginRepo.findAll(pageable);
            } else {
                log.debug("Searching by :" + search);
                //creating example by pompiste / chauffeur / code bon / code engin
                BonEngin bonEngin = new BonEngin();
                bonEngin.setCode(search);
                bonEngin.setCompteurAbsoluKm(null);
                bonEngin.setCompteurAbsoluH(null);
                bonEngin.setConsommationH(null);
                bonEngin.setConsommationKm(null);
                bonEngin.setQuantite(null);
                bonEngin.setCompteurHenPanne(null);
                bonEngin.setCompteurKmenPanne(null);
                Employe employe = null;
                Engin engin = null;
                if (employeRepo.findByNom(search).isPresent()) {
                    employe = employeRepo.findByNom(search).get();
                }
                if (enginRepo.findByCode(search).isPresent()) {
                    engin = enginRepo.findByCode(search).get();
                }
                bonEngin.setPompiste(employe);
                bonEngin.setChauffeur(employe);
                bonEngin.setEngin(engin);
                //creating matcher
                ExampleMatcher matcher = ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues();
                Example<BonEngin> example = Example.of(bonEngin, matcher);
                log.debug("getting search results");
                return bonEnginRepo.findAll(example, pageable);
            }
        } catch (Exception e) {
            log.debug("Failed retrieving list of bons");
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
    @Override
    public boolean hasErrors(BonEngin bon) {
        fillBon(bon);
        BonEngin lastBonEngin = getLastBonEngin(bon.getEngin());
        if (lastBonEngin != null)
            return !(hasLogicQuantite(bon) && hasLogicCompteur(bon, lastBonEngin));
        else
            return !hasLogicQuantite(bon);
    }

    private BonEngin fillBon(BonEngin bon) {
        try {
            bon.setEngin(enginRepo.findById(bon.getEngin().getId()).get());
            bon.setChauffeur(employeRepo.findById(bon.getChauffeur().getId()).get());
            bon.setPompiste(employeRepo.findById(bon.getPompiste().getId()).get());
            bon.setChantierTravail(chantierRepo.findById(bon.getChantierTravail().getId()).get());
            bon.setChantierGazoil(chantierRepo.findById(bon.getChantierGazoil().getId()).get());
            calculCompteursAbsolu(bon);
            return bon;
        } catch (Exception e) {
            throw new OperationFailedException("Operation echouée", e);
        }
    }

    /**
     * @param bon the current BonEngin
     * @return boolean value
     * 0 if one of (quantity or meter) hasn't a logic value
     * 1 else ( if compteur and quantité are logic )
     */
    @Override
    public boolean hasErrorsAttention(BonEngin bon) {
        BonEngin bonEngin = getLastBonEngin(bon.getEngin());
        if (bonEngin == null) return false;
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.H)) {
            return (bon.getCompteurH() < bonEngin.getCompteurH());
        }
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.KM)) {
            return (bon.getCompteurKm() < bonEngin.getCompteurKm());
        }
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.KM_H)) {
            return (bon.getCompteurH() < bonEngin.getCompteurH()) || (bon.getCompteurKm() < bonEngin.getCompteurKm());
        }
        return false;
    }

    /* ------------------------------------------------------------------------------ */
    /* ------------ PRIVATE METHODES ----------------------------------------------- */
    /* ---------------------------------------------------------------------------- */

    private void insertAlertes(BonEngin bonEngin) {
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

    private void insertAlerte(BonEngin bonEngin, String msg, TypeAlerte type) {
        Alerte alerte = new Alerte();
        alerte.setDate(LocalDate.now());
        alerte.setIdBon(bonEngin.getId());
        alerte.setMessage(msg);
        alerte.setEtat(true);
        alerte.setTypeAlerte(type);
        alerteService.add(alerte);
    }

    /**
     * @param bonEngin the current bon
     * @return boolean value
     * 0 if quantity isn't logic
     * 1 else
     */
    private boolean hasLogicQuantite(BonEngin bonEngin) {
        log.info("Service : Testing if Quantité Logic");
        return (bonEngin.getQuantite() < 2 * bonEngin.getEngin().getSousFamille().getCapaciteReservoir());
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
        long days;
        days = Period.between(lastBonEngin.getDate(), bonEngin.getDate()).getDays();
        if (days <= 0) days = 1;
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

    private void insertStock(BonEngin bonEngin) {
        Stock stock = new Stock();
        stock.setChantier(bonEngin.getChantierTravail());
        stock.setDate(bonEngin.getDate());
        stock.setSortieE(bonEngin.getQuantite());
        stockService.add(stock);
    }

    private void insertBonLivraison(BonEngin bonEngin) {
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

    private BonEngin calculConsommation(BonEngin bonEngin) {
        //get last bon Engin [ BX ] with cmpEnpanne=non && plein =oui
        //get list of bons between BX and the current bon
        //SOM_Q = calculate lmejmou3 dial les quantite ,
        // AA = absolu current bon - absolu BX
        //calculate consommatio = SOM_Q/AA ( l7ala dial km : SOM_Q*100/AA
        TypeCompteur typeCompteur = bonEngin.getEngin().getSousFamille().getTypeCompteur();
        BonEngin lastBon, lastBon2;
        long som_Q = 0;
        long som_Q_2 = 0;
        if (typeCompteur.equals(TypeCompteur.H)) {
            lastBon = bonEnginRepo.findLastBonEnginH_toConsommation(bonEngin.getEngin());
            if (lastBon != null) {
                for (BonEngin b : bonEnginRepo.findAllBetweenLastBonAndCurrentBon_H(lastBon.getCompteurAbsoluH()))
                    som_Q += b.getQuantite();
                bonEngin.setConsommationH((float) som_Q / (bonEngin.getCompteurAbsoluH() - lastBon.getCompteurAbsoluH()));
            }
            if (bonEngin.getConsommationH() > bonEngin.getEngin().getSousFamille().getConsommationHMax())
                insertAlerte(bonEngin, "La consommation H est Annormale", TypeAlerte.CONSOMMATION_H_ANNORMALE);
        }
        if (typeCompteur.equals(TypeCompteur.KM)) {
            lastBon = bonEnginRepo.findLastBonEnginKm_toConsommation(bonEngin.getEngin());
            if (lastBon != null) {
                for (BonEngin b : bonEnginRepo.findAllBetweenLastBonAndCurrentBon_Km(lastBon.getCompteurAbsoluKm()))
                    som_Q += b.getQuantite();
                bonEngin.setConsommationKm((float) som_Q * 100 / (bonEngin.getCompteurAbsoluKm() - lastBon.getCompteurAbsoluKm()));
            }
            if (bonEngin.getConsommationKm() > bonEngin.getEngin().getSousFamille().getConsommationKmMax())
                insertAlerte(bonEngin, "La consommation Km est Annormale", TypeAlerte.CONSOMMATION_KM_ANNORMALE);

        }
        if (typeCompteur.equals(TypeCompteur.KM_H)) {
            lastBon = bonEnginRepo.findLastBonEnginKm_toConsommation(bonEngin.getEngin());
            lastBon2 = bonEnginRepo.findLastBonEnginH_toConsommation(bonEngin.getEngin());
            if (lastBon != null) {
                for (BonEngin b : bonEnginRepo.findAllBetweenLastBonAndCurrentBon_Km(lastBon.getCompteurAbsoluKm())) {
                    if (b.getQuantite() != null)
                        som_Q += b.getQuantite();
                }
                bonEngin.setConsommationKm((float) som_Q * 100 / (bonEngin.getCompteurAbsoluKm() - lastBon.getCompteurAbsoluKm()));
            }
            if (lastBon2 != null) {
                for (BonEngin b : bonEnginRepo.findAllBetweenLastBonAndCurrentBon_H(lastBon.getCompteurAbsoluH())) {
                    if (b.getQuantite() != null)
                        som_Q_2 += b.getQuantite();
                }
                bonEngin.setConsommationH((float) som_Q_2 / (bonEngin.getCompteurAbsoluH() - lastBon2.getCompteurAbsoluH()));
            }
            if (bonEngin.getConsommationKm() > bonEngin.getEngin().getSousFamille().getConsommationKmMax())
                insertAlerte(bonEngin, "La consommation Km est Annormale", TypeAlerte.CONSOMMATION_KM_ANNORMALE);
            if (bonEngin.getConsommationH() > bonEngin.getEngin().getSousFamille().getConsommationHMax())
                insertAlerte(bonEngin, "La consommation H est Annormale", TypeAlerte.CONSOMMATION_H_ANNORMALE);
        }
        return bonEngin;
    }

    /**
     * @param engin the Engin of the bon
     * @return Bon Engin
     * The last bonEngin having the same Engin
     */
    private BonEngin getLastBonEngin(Engin engin) {
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
