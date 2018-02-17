package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.repo.others.EmployeRepo;
import me.kadarh.mecaworks.service.EmployeService;
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
			if (employe.getNom() != null) {
				old.setNom(employe.getNom());
			}
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
	 * @param id of employe to delete
	 */
	@Override
	public void delete(Long id) {
		log.info("Service= employeServiceImpl - calling methode update");
		try {
			employeRepo.deleteById(id);
		} catch (Exception e) {
			log.debug("cannot delete employe , failed operation");
			throw new OperationFailedException("La suppression du employe a echouée ", e);
		}
	}
}
