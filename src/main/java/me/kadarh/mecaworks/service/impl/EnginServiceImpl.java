package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Groupe;
import me.kadarh.mecaworks.domain.others.Marque;
import me.kadarh.mecaworks.domain.others.SousFamille;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.repo.others.GroupeRepo;
import me.kadarh.mecaworks.repo.others.MarqueRepo;
import me.kadarh.mecaworks.repo.others.SousFamilleRepo;
import me.kadarh.mecaworks.service.EnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class EnginServiceImpl implements EnginService {

	private EnginRepo enginRepo;
	private SousFamilleRepo sousFamilleRepo;
	private GroupeRepo groupeRepo;
    private MarqueRepo marqueRepo;

    public EnginServiceImpl(EnginRepo enginRepo, SousFamilleRepo sousFamilleRepo, GroupeRepo groupeRepo, MarqueRepo marqueRepo) {
        this.enginRepo = enginRepo;
        this.sousFamilleRepo = sousFamilleRepo;
        this.groupeRepo = groupeRepo;
        this.marqueRepo = marqueRepo;
    }

	/**
	 * @param engin to add
	 * @return the engin
	 */
	@Override
	public Engin add(Engin engin) {
		log.info("Service = EnginServiceImpl - calling methode add");
		try {
            engin.setGroupe(groupeRepo.findByNom(engin.getGroupe().getNom()).get());
            engin.setSousFamille(sousFamilleRepo.findByNom(engin.getSousFamille().getNom()).get());
            engin.setMarque(marqueRepo.findByNom(engin.getMarque().getNom()).get());
            return enginRepo.save(engin);
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
			Engin old = enginRepo.findById(engin.getId()).get();
			if (engin.getCompteurInitialKm() != null)
				old.setCompteurInitialKm(engin.getCompteurInitialKm());
			if (engin.getCompteurInitialL() != null)
				old.setCompteurInitialL(engin.getCompteurInitialL());
			if (engin.getNumeroSerie() != null)
				old.setNumeroSerie(engin.getNumeroSerie());
			if (engin.getCode() != null)
				old.setCode(engin.getCode());
			if (engin.getGroupe().getNom() != null)
				old.setGroupe(groupeRepo.findByNom(engin.getGroupe().getNom()).get());
            if (engin.getMarque() != null)
                old.setMarque(marqueRepo.findByNom(engin.getMarque().getNom()).get());
            if (engin.getSousFamille().getNom() != null)
				old.setSousFamille(sousFamilleRepo.findByNom(engin.getSousFamille().getNom()).get());
			return enginRepo.save(engin);
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
                //marque
                Marque marque = new Marque();
                marque.setNom(search);
                //setting groupe , marque , soufamille to engin
                engin.setGroupe(groupe);
                engin.setMarque(marque);
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
        log.info("Service- EnginServiceImpl Calling delete with params id =" + id);
        try {
            enginRepo.delete(enginRepo.findById(id).get());
        } catch (Exception e) {
            log.debug("Failed retrieving list of engins");
            throw new OperationFailedException("Operation de suppression de l'engin (id=" + id + ") a echouée", e);
        }
    }
}
