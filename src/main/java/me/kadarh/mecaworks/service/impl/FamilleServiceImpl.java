package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Classe;
import me.kadarh.mecaworks.domain.others.Famille;
import me.kadarh.mecaworks.repo.others.FamilleRepo;
import me.kadarh.mecaworks.service.ClasseService;
import me.kadarh.mecaworks.service.FamilleService;
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
public class FamilleServiceImpl implements FamilleService {

	private FamilleRepo familleRepo;

	private ClasseService classeService;

	public FamilleServiceImpl(FamilleRepo familleRepo, ClasseService classeService) {
		this.familleRepo = familleRepo;
		this.classeService = classeService;
	}

	/**
	 * @param famille to add
	 * @return the famille
	 */
	@Override
	public Famille add(Famille famille) {
		log.info("Service= FamilleServiceImpl - calling methode add");
		try {
			famille.setClasse(classeService.get(famille.getClasse().getId()));
			return familleRepo.save(famille);
		} catch (ResourceNotFoundException e) {
			log.debug("cannot add famille , classe not found");
			throw new OperationFailedException("L'ajout de la famille a echouée ", e);
		} catch (Exception e) {
			log.debug("cannot add famille , failed operation");
			throw new OperationFailedException("L'ajout de la famille a echouée ", e);
		}
	}

	/**
	 * @param famille to add
	 * @return the famille
	 */
	@Override
	public Famille update(Famille famille) {
		log.info("Service= FamilleServiceImpl - calling methode update");
		try {
			Famille old = get(famille.getId());
			if (famille.getNom() != null) {
				old.setNom(famille.getNom());
			}
			if (famille.getClasse() != null) {
				old.setClasse(famille.getClasse());
			}
			return familleRepo.save(old);
		} catch (Exception e) {
			log.debug("cannot update famille , failed operation");
			throw new OperationFailedException("La modification de la famille a echouée ", e);
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
	public Page<Famille> familleList(Pageable pageable, String search) {
		log.info("Service- FamilleServiceImpl Calling familleList with params :" + pageable + ", " + search);
		try {
			if (search.isEmpty()) {
				log.debug("fetching famille page");
				return familleRepo.findAll(pageable);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				Famille famille = new Famille();
				famille.setNom(search);
				Classe classe = new Classe();
				classe.setNom(search);
				famille.setClasse(classe);
				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<Famille> example = Example.of(famille, matcher);
				log.debug("getting search results");
				return familleRepo.findAll(example, pageable);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of familles");
			throw new OperationFailedException("Operation échouée", e);
		}
	}

	@Override
	public Famille get(Long id) {
		log.info("Service-FamilleServiceImpl Calling getFamille with params :" + id);
		try {
			return familleRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			log.info("Problem , cannot find Famille with id = :" + id);
			throw new ResourceNotFoundException("Famille introuvable", e);
		} catch (Exception e) {
			log.info("Problem , cannot get Famille with id = :" + id);
			throw new OperationFailedException("Problème lors de la recherche de la Famille", e);
		}
	}

	/**
	 * @param id of famille to delete
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= FamilleServiceImpl - calling methode delete with id = " + id);
		try {
			familleRepo.deleteById(id);
		} catch (NoSuchElementException e) {
			log.debug("cannot delete famille , failed operation");
			throw new ResourceNotFoundException("La suppression de la famille a echouée , la famille n'existe pas", e);
		}
	}

	/**
	 * @return List of All Familles in database
	 */
	@Override
	public List<Famille> list() {
		log.info("Service= FamilleServiceImpl - calling methode list()");
		try {
			return familleRepo.findAll();
		} catch (Exception e) {
			log.debug("cannot fetch list familles , failed operation");
			throw new OperationFailedException("La recherche des familles a echouée ", e);
		}
	}
}
