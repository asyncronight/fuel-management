package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.others.EmployeRepo;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.impl.bons.bonEngin.CalculServiceImpl;
import me.kadarh.mecaworks.service.impl.bons.bonEngin.CheckServiceImpl;
import me.kadarh.mecaworks.service.impl.bons.bonEngin.PersistServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author kadarH
 */

@Service
@Slf4j
@Transactional
public class BonEnginServiceImpl implements BonEnginService {

    private final BonEnginRepo bonEnginRepo;
    private final EnginRepo enginRepo;
    private final EmployeRepo employeRepo;
    private CalculServiceImpl calculService;
    private CheckServiceImpl checkService;
    private PersistServiceImpl persistService;

    public BonEnginServiceImpl(BonEnginRepo bonEnginRepo, EnginRepo enginRepo, EmployeRepo employeRepo, CalculServiceImpl calculService, CheckServiceImpl checkService, PersistServiceImpl persistService) {
        this.bonEnginRepo = bonEnginRepo;
        this.enginRepo = enginRepo;
        this.employeRepo = employeRepo;
        this.calculService = calculService;
        this.checkService = checkService;
        this.persistService = persistService;
    }
    /* ------------------------------------------------------------------------------ */
    /* ------------ CRUD METHODES -------------------------------------------------- */
    /* ---------------------------------------------------------------------------- */

    @Override
    public BonEngin add(BonEngin bonEngin) {
        log.info("Service= BonEnginServiceImpl - calling methode add");
        calculService.fillBon(bonEngin);
        try {
            if (bonEngin.getPlein())
                bonEngin = calculService.calculConsommation(bonEngin);
            if (bonEngin.getChantierGazoil() != bonEngin.getChantierTravail())
                persistService.insertBonLivraison(bonEngin);
            bonEngin = bonEnginRepo.save(bonEngin);
            persistService.insertAlertes(bonEngin);
            persistService.insertStock(bonEngin);
            persistService.insertAlerteConsommation(bonEngin);
            return bonEngin;
        } catch (DataIntegrityViolationException e) {
            log.debug("cannot add bonEngin , failed operation");
            throw new OperationFailedException("L'ajout du bonEngin a echouée , le code engin existe déjà", e);
        } catch (Exception e) {
            log.debug("cannot add bonEngin , failed operation");
            throw new OperationFailedException("L'ajout du bonEngin a echouée ", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            bonEnginRepo.delete(bonEnginRepo.findById(id).get());
        } catch (Exception e) {
            throw new OperationFailedException("Probleme lors de la suppression du bon, ce bon ne peut pas être supprimer", e);
        }
    }

	/**
	 * Search by Pompiste, Chauffeur, code, engin and date
	 *
	 * @param pageable
	 * @param search
	 * @return
	 */
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
				bonEngin.setCompteurKm(null);
				bonEngin.setCompteurH(null);
				bonEngin.setCompteurAbsoluH(null);
				bonEngin.setConsommationH(null);
				bonEngin.setConsommationKm(null);
				bonEngin.setQuantite(null);
				bonEngin.setCarburant(null);
				bonEngin.setCompteurPompe(null);
				bonEngin.setCompteurHenPanne(null);
				bonEngin.setCompteurKmenPanne(null);
				Employe employe = new Employe();
				employe.setNom(search);
				Engin engin = new Engin();
				engin.setNumeroSerie(search);

				bonEngin.setPompiste(employe);
				bonEngin.setChauffeur(employe);
				bonEngin.setEngin(engin);

				try {
					bonEngin.setDate(LocalDate.parse(search, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				} catch (Exception e) {
					log.debug("Cannot search by date : keyword doesn't match date pattern");
				}
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
        calculService.fillBon(bon);
        BonEngin lastBonEngin = persistService.getLastBonEngin(bon.getEngin());
        if (lastBonEngin != null)
            return !(checkService.hasLogicQuantite(bon) && checkService.hasLogicCompteur(bon, lastBonEngin) && checkService.hasLogicDate(bon, lastBonEngin) && checkService.hasLogicDateAndCompteur(bon, lastBonEngin));
        else
            return !checkService.hasLogicQuantite(bon);
    }

    /**
     * @param bon the current BonEngin
     * @return boolean value
     * 0 if one of (quantity or meter) hasn't a logic value
     * 1 else ( if compteur and quantité are logic )
     */
    @Override
    public boolean hasErrorsAttention(BonEngin bon) {
        calculService.fillBon(bon);
        BonEngin bonEngin = persistService.getLastBonEngin(bon.getEngin());
        if (bonEngin == null) return false;
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.H)) {
            return (bon.getCompteurH() < bonEngin.getCompteurH() && !bon.getCompteurHenPanne());
        }
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.KM)) {
            return (bon.getCompteurKm() < bonEngin.getCompteurKm());
        }
        if (bon.getEngin().getSousFamille().getTypeCompteur().equals(TypeCompteur.KM_H)) {
            return (bon.getCompteurH() < bonEngin.getCompteurH()) || (bon.getCompteurKm() < bonEngin.getCompteurKm());
        }
        return false;
    }


}
