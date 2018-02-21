package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Fournisseur;
import me.kadarh.mecaworks.repo.others.FournisseurRepo;
import me.kadarh.mecaworks.service.FournisseurService;
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
public class FournisseurServiceImpl implements FournisseurService {

	private FournisseurRepo fournisseurRepo;

	public FournisseurServiceImpl(FournisseurRepo fournisseurRepo) {
		this.fournisseurRepo = fournisseurRepo;
	}

	/**
	 * @param fournisseur to add
	 * @return the Fournisseur
	 */
	@Override
	public Fournisseur add(Fournisseur fournisseur) {
		log.info("Service= FournisseurServiceImpl - calling methode add");
		try {
			return fournisseurRepo.save(fournisseur);
		} catch (Exception e) {
			log.debug("cannot add Fournisseur , failed operation");
			throw new OperationFailedException("L'ajout du fournisseurs a echouée ", e);
		}
	}

	/**
	 * @param fournisseur to add
	 * @return the Fournisseur
	 */
	@Override
	public Fournisseur update(Fournisseur fournisseur) {
		log.info("Service= FournisseurServiceImpl - calling methode update");
		try {
			Fournisseur old = fournisseurRepo.findById(fournisseur.getId()).get();
			if (fournisseur.getNom() != null) {
				old.setNom(fournisseur.getNom());
			}
			return fournisseurRepo.save(old);

		} catch (Exception e) {
			log.debug("cannot update Fournisseur , failed operation");
			throw new OperationFailedException("La modification du Fournisseur a echouée ", e);
		}
	}

	/**
	 * search with nom
	 *
	 * @param pageable page description
	 * @param search   keyword
	 * @return Page
	 */
	@Override
	public Page<Fournisseur> fournisseurList(Pageable pageable, String search) {
		log.info("Service- FournisseurServiceImpl Calling FournisseurList with params :" + pageable + ", " + search);
		try {
			if (search.isEmpty()) {
				log.debug("fetching Fournisseur page");
				return fournisseurRepo.findAll(pageable);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				Fournisseur fournisseur = new Fournisseur();
				fournisseur.setNom(search);
				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<Fournisseur> example = Example.of(fournisseur, matcher);
				log.debug("getting search results");
				return fournisseurRepo.findAll(example, pageable);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of Fournisseurs");
			throw new OperationFailedException("Operation échouée", e);
		}
	}

	@Override
	public Fournisseur get(Long id) {
		log.info("Service-FournisseurServiceImpl Calling getFournisseur with params :" + id);
		try {
			return fournisseurRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			log.info("Problem , cannot find Fournisseur with id = :" + id);
			throw new ResourceNotFoundException("Fournisseur introuvable", e);
		} catch (Exception e) {
			log.info("Problem , cannot get Fournisseur with id = :" + id);
			throw new OperationFailedException("Problème lors de la recherche du Fournisseur", e);
		}
	}

	/**
	 * @param id of Fournisseur to delete
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= FournisseurServiceImpl - calling methode update");
		try {
			//fournisseurRepo.deleteById(id);
		} catch (Exception e) {
			log.debug("cannot delete Fournisseur , failed operation");
			throw new OperationFailedException("La suppression du Fournisseur a echouée ", e);
		}
	}
}
