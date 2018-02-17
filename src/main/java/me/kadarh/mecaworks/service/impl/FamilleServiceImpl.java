package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Famille;
import me.kadarh.mecaworks.repo.others.FamilleRepo;
import me.kadarh.mecaworks.service.FamilleService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class FamilleServiceImpl implements FamilleService {

	private FamilleRepo familleRepo;

	public FamilleServiceImpl(FamilleRepo familleRepos) {
		this.familleRepo = familleRepos;
	}

	/**
	 * @param famille to add
	 * @return the famille
	 */
	@Override
	public Famille add(Famille famille) {
		log.info("Service= FamilleServiceImpl - calling methode add");
		try {
			return familleRepo.save(famille);
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
			Famille old = familleRepo.findById(famille.getId()).get();
			if (famille.getNom() != null) {
				old.setNom(famille.getNom());
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

	/**
	 * @param id of famille to delete
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= FamilleServiceImpl - calling methode update");
		try {
			familleRepo.deleteById(id);
		} catch (Exception e) {
			log.debug("cannot delete famille , failed operation");
			throw new OperationFailedException("La suppression de la famille a echouée ", e);
		}
	}
}
