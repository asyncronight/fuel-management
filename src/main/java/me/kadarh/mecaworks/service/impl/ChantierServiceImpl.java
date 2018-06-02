package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.repo.others.ChantierRepo;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.StockService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class ChantierServiceImpl implements ChantierService {

	private ChantierRepo chantierRepo;
	private StockService stockService;

	public ChantierServiceImpl(ChantierRepo chantierRepo, StockService stockService) {
		this.chantierRepo = chantierRepo;
		this.stockService = stockService;
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
			Chantier old = get(chantier.getId());
			if (chantier.getNom() != null) {
				old.setNom(chantier.getNom());
			}
			if (chantier.getAdresse() != null) {
				old.setAdresse(chantier.getAdresse());
			}
			if (chantier.getStock() != null) {
				if (!chantier.getStock().equals(old.getStock())) {
					Stock stock = new Stock();
					stock.setDate(LocalDate.now());
					stock.setStockC(chantier.getStock());
					stock.setStockReel(chantier.getStock());
					stock.setChantier(old);
					Integer stockC = stockService.getLastStock(chantier).getStockC();
					if (stockC < chantier.getStock())
						stock.setEcart_plus(chantier.getStock() - stockC);
					else if (stockC > chantier.getStock())
						stock.setEcart_moins(stockC - chantier.getStock());
					stockService.add(stock);
				}
				old.setStock(chantier.getStock());
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
	 * @param id of the chantier
	 * @return chantier
	 */
	@Override
	public Chantier get(Long id) {
		log.info("Service- ChantierServiceImpl Calling getChantier with params :" + id);
		try {
			return chantierRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			log.info("Problem , cannot find chantier with id = :" + id);
			throw new ResourceNotFoundException("chantier introuvable", e);
		} catch (Exception e) {
			log.info("Problem , cannot get chantier with id = :" + id);
			throw new OperationFailedException("Problème lors de la recherche du chantier", e);
		}
	}

	/**
	 * @param id of chantier to delete
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= ChantierServiceImpl - calling methode delete");
		try {
			chantierRepo.deleteById(id);
		} catch (Exception e) {
			log.debug("cannot delete chantier , failed operation");
			throw new OperationFailedException("La suppression du chantier a echouée ", e);
		}
	}


	/**
	 * @return List of chantiers in database
	 */
	@Override
	public List<Chantier> getList() {
		log.info("Service= ChantierServiceImpl - calling methode getList");
		try {
			return chantierRepo.findAll();
		} catch (Exception e) {
			log.debug("cannot fetch list chantiers , failed operation");
			throw new OperationFailedException("La recherche des chantiers a echouée ", e);
		}
	}
}
