package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.Alerte;
import me.kadarh.mecaworks.repo.others.AlerteRepo;
import me.kadarh.mecaworks.service.AlerteService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class AlerteServiceImpl implements AlerteService {

	private final AlerteRepo alerteRepo;

	public AlerteServiceImpl(AlerteRepo alerteRepo) {
		this.alerteRepo = alerteRepo;
	}

	@Override
	public Alerte add(Alerte alerte) {
		log.info("Service= AlerteServiceImpl - calling methode add");
		try {
			return alerteRepo.save(alerte);
		} catch (Exception e) {
			log.debug("cannot add alerte , failed operation");
			throw new OperationFailedException("L'ajout de l'alerte a echoué ", e);
		}
	}

	@Override
	public Page<Alerte> getPage(Pageable pageable, String search) {
		log.info("Service- BonEnginServiceImpl Calling bonEnginList with params :" + pageable + ", " + search);
		try {
			Pageable pageable1 = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "date"));

			if (search.isEmpty()) {
				log.debug("fetching Alerte page");
				return alerteRepo.findAll(pageable1);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				Alerte alerte = new Alerte();
				alerte.setMessage(search);
				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<Alerte> example = Example.of(alerte, matcher);
				log.debug("getting search results");
				return alerteRepo.findAll(example, pageable1);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of bons");
			throw new OperationFailedException("Operation échouée", e);
		}
	}

}