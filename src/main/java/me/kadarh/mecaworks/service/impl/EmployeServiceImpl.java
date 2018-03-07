package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.repo.others.EmployeRepo;
import me.kadarh.mecaworks.service.EmployeService;
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
public class EmployeServiceImpl implements EmployeService {

	private EmployeRepo employeRepo;

	public EmployeServiceImpl(EmployeRepo employeRepo) {
		this.employeRepo = employeRepo;
	}

	/**
	 * @param employe to add
	 * @return the employe
	 */
	@Override
	public Employe add(Employe employe) {
		log.info("Service= EmployeServiceImpl - calling methode add");
		try {
			return employeRepo.save(employe);
		} catch (Exception e) {
			log.debug("cannot add employe , failed operation");
			throw new OperationFailedException("L'ajout du employe a echouée ", e);
		}
	}

	/**
	 * @param employe to add
	 * @return the employe
	 */
	@Override
	public Employe update(Employe employe) {
		log.info("Service= EmployeServiceImpl - calling methode update");
		try {
			Employe old = employeRepo.findById(employe.getId()).get();
			if (employe.getNom() != null)
				old.setNom(employe.getNom());
			if (employe.getMetier() != null)
				old.setMetier(employe.getMetier());
			return employeRepo.save(old);

		} catch (Exception e) {
			log.debug("cannot update employe , failed operation");
			throw new OperationFailedException("La modification du employe a echouée ", e);
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
	public Page<Employe> employesList(Pageable pageable, String search) {
		log.info("Service- employeServiceImpl Calling employeList with params :" + pageable + ", " + search);
		try {
			if (search.isEmpty()) {
				log.debug("fetching employe page");
				return employeRepo.findAll(pageable);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				Employe employe = new Employe();
				employe.setNom(search);
				employe.setMetier(search);
				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<Employe> example = Example.of(employe, matcher);
				log.debug("getting search results");
				return employeRepo.findAll(example, pageable);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of employes");
			throw new OperationFailedException("Operation échouée", e);
		}
	}


	/**
	 * @param id
	 * @return void
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= EmployeServiceImpl - calling methode delete with id = " + id);
		try {
			employeRepo.deleteById(id);
		} catch (NoSuchElementException e) {
			log.debug("cannot delete employe with id = " + id + " , failed operation");
			throw new ResourceNotFoundException("La suppression de l'employe a echouée , l'employe n'existe pas", e);
		}
	}

	/**
	 * @param id of the employe
	 * @return employe
	 */
	@Override
	public Employe get(Long id) {
		log.info("Service- EmployeServiceImpl Calling get with params :" + id);
		try {
			return employeRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			log.info("Problem , cannot find employe with id = :" + id);
			throw new ResourceNotFoundException("employe introuvable", e);
		} catch (Exception e) {
			log.info("Problem , cannot get employe with id = :" + id);
			throw new OperationFailedException("Problème lors de la recherche de l'employe", e);
		}
	}

	/**
	 * @return List of All Employes in database
	 */
	@Override
	public List<Employe> getList() {
		log.info("Service= EmployeServiceImpl - calling methode getList");
		try {
			return employeRepo.findAll();
		} catch (Exception e) {
			log.debug("cannot fetch list employes , failed operation");
			throw new OperationFailedException("La recherche des employes a echouée ", e);
		}
	}
}
