package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.repo.others.ChantierRepo;
import me.kadarh.mecaworks.service.ChantierService;
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
public class ChantierServiceImpl implements ChantierService {

	private ChantierRepo chantierRepo;

	public ChantierServiceImpl(ChantierRepo chantierRepo) {
		this.chantierRepo = chantierRepo;
	}

	/**
	 * @param chantier to add
	 * @return the chantier
	 */
	@Override
	public Chantier add(Chantier chantier) {
		log.info("Service= ChantierServiceImpl - calling methode add");
		try {
			return chantierRepo.save(chantier);
		} catch (Exception e) {
			log.debug("cannot add chantier , failed operation");
			throw new OperationFailedException("L'ajout du Chantier a echouée ", e);
		}
	}

	/**
	 * updating the chantier
	 *
	 * @param chantier
	 * @return
	 */
	@Override
	public Chantier update(Chantier chantier) {
		log.info("Service= ChantierServiceImpl - calling methode update");
		try {
			Chantier old = chantierRepo.findById(chantier.getId()).get();
			if (chantier.getNom() != null) {
				old.setNom(chantier.getNom());
			}
			if (chantier.getAdresse() != null) {
				old.setAdresse(chantier.getAdresse());
			}
			if (chantier.getStock() != null) {
				old.setStock(chantier.getStock());
				//Todo : persisting Stock table ( inventaire )
				//Hint : Autowire chantierService and get by id - old.setStock(chantierService.fbi(chantier.stock.id))
			}
			return chantierRepo.save(old);

		} catch (Exception e) {
			log.debug("cannot update Chantier , failed operation");
			throw new OperationFailedException("La modification du Chantier a echouée ", e);
		}
	}

	/**
	 * search with nom and adresse
	 *
	 * @param pageable page description
	 * @param search   keyword
	 * @return Page
	 */
	@Override
	public Page<Chantier> chantierList(Pageable pageable, String search) {
		log.info("Service- ChantierServiceImpl Calling chantierList with params :" + pageable + ", " + search);
		try {
			if (search.isEmpty()) {
				log.debug("fetching chantier page");
				return chantierRepo.findAll(pageable);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				Chantier chantier = new Chantier();
				chantier.setNom(search);
				chantier.setAdresse(search);
				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<Chantier> example = Example.of(chantier, matcher);
				log.debug("getting search results");
				return chantierRepo.findAll(example, pageable);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of chantiers");
			throw new OperationFailedException("Operation échouée", e);
		}
	}


	/**
	 * @param id of chantier to delete
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= ChantierServiceImpl - calling methode update");
		try {
			chantierRepo.deleteById(id);
		} catch (Exception e) {
			log.debug("cannot delete chantier , failed operation");
			throw new OperationFailedException("La suppression du chantier a echouée ", e);
		}
	}
}
