package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Groupe;
import me.kadarh.mecaworks.domain.others.SousFamille;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.service.EnginService;
import me.kadarh.mecaworks.service.GroupeService;
import me.kadarh.mecaworks.service.SousFamilleService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class EnginServiceImpl implements EnginService {

	private final EnginRepo enginRepo;
	private final SousFamilleService sousFamilleService;
	private final GroupeService groupeService;

	public EnginServiceImpl(EnginRepo enginRepo, SousFamilleService sousFamilleService, GroupeService groupeService) {
		this.enginRepo = enginRepo;
		this.sousFamilleService = sousFamilleService;
		this.groupeService = groupeService;
	}

	/**
	 * @param engin to add
	 * @return the engin
	 */
	@Override
	public Engin add(Engin engin) {
		log.info("Service = EnginServiceImpl - calling methode add");
		try {
			engin.setGroupe(groupeService.get(engin.getGroupe().getId()));
			engin.setSousFamille(sousFamilleService.get(engin.getSousFamille().getId()));
			return enginRepo.save(engin);
		} catch (ResourceNotFoundException e) {
			log.debug("cannot find group or sousFamille , failed operation");
			throw new OperationFailedException("L'ajout de l'engin a echouée,sous famille ou groupe introuvable ", e);
		} catch (Exception e) {
			log.debug("cannot add engin , failed operation");
			throw new OperationFailedException("L'ajout de l'engin a echouée ", e);
		}
	}

	/**
	 * @param engin to update
	 * @return engin updated
	 */
	@Override
	public Engin update(Engin engin) {
		log.info("Service = EnginServiceImpl - calling methode update");
		try {
			Engin old = get(engin.getId());
			if (engin.getCompteurInitialKm() != null)
				old.setCompteurInitialKm(engin.getCompteurInitialKm());
            if (engin.getCompteurInitialH() != null)
                old.setCompteurInitialH(engin.getCompteurInitialH());
            if (engin.getNumeroSerie() != null)
				old.setNumeroSerie(engin.getNumeroSerie());
			if (engin.getCode() != null)
				old.setCode(engin.getCode());
            if (engin.getGroupe() != null)
	            old.setGroupe(groupeService.get(engin.getGroupe().getId()));
            if (engin.getObjectif() != null)
                old.setObjectif(engin.getObjectif());
            if (engin.getSousFamille() != null)
	            old.setSousFamille(sousFamilleService.get(engin.getSousFamille().getId()));
            return enginRepo.save(old);
		} catch (ResourceNotFoundException e) {
			log.debug("cannot find group or sousFamille , failed operation");
			throw new OperationFailedException("La modification de l'engin a echouée,sous famille ou groupe introuvable ", e);
		} catch (Exception e) {
			log.debug("cannot update engin , failed operation");
			throw new OperationFailedException("La modification de l'engin a echouée ", e);
		}
	}

	@Override
    public Engin get(Long id) {
        log.info("Service- EnginServiceImpl Calling getEngin with params :" + id);
        try {
            return enginRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find engin with id = :" + id);
            throw new ResourceNotFoundException("Engin introuvable", e);
        } catch (Exception e) {
            log.info("Problem , cannot get engin with id = :" + id);
            throw new OperationFailedException("Problème lors de la recherche de l'engin", e);
        }
    }

	@Override
    public Page<Engin> enginList(Pageable pageable, String search) {
        log.info("Service- EnginServiceImpl Calling enginList with params :" + pageable + ", " + search);
        try {
            if (search.isEmpty()) {
                log.debug("fetching engin page");
                return enginRepo.findAll(pageable);
            } else {
                log.debug("Searching by :" + search);
                //creating example
                Engin engin = new Engin();
                engin.setNumeroSerie(search);
                engin.setCode(search);
                //sousfamille
                SousFamille sousFamille = new SousFamille();
                sousFamille.setNom(search);
                //groupe
                Groupe groupe = new Groupe();
                groupe.setNom(search);
	            //setting groupe , soufamille to engin
                engin.setGroupe(groupe);
                engin.setSousFamille(sousFamille);
                //creating matcher
                ExampleMatcher matcher = ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues();
                Example<Engin> example = Example.of(engin, matcher);
                log.debug("getting search results");
                return enginRepo.findAll(example, pageable);
            }
        } catch (Exception e) {
            log.debug("Failed retrieving list of engins");
            throw new OperationFailedException("Operation échouée, problème de saisi", e);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Service= EnginServiceImpl - calling methode delete with id = " + id);
        try {
            enginRepo.deleteById(id);
        } catch (Exception e) {
            log.debug("cannot delete engin , failed operation");
            throw new OperationFailedException("La suppression de l'engin a echouée ", e);
        }
    }

    /**
     * @return List of All Engins in database
     */
    @Override
    public List<Engin> getList() {
        log.info("Service= EnginServiceImpl - calling methode getList");
        try {
            return enginRepo.findAll();
        } catch (Exception e) {
            log.debug("cannot fetch list engins , failed operation");
            throw new OperationFailedException("La recherche des engins a echouée ", e);
        }
    }
}
