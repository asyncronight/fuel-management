package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.Alerte;
import me.kadarh.mecaworks.repo.others.AlerteRepo;
import me.kadarh.mecaworks.service.AlerteService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void hideAlert(Long id) {
        log.info("Service- BonEnginServiceImpl Calling hideAlert with params :" + id);
        try {
            Alerte alerte = alerteRepo.getOne(id);
            alerte.setEtat(false);
            alerteRepo.save(alerte);
        } catch (Exception e) {
            log.debug("Failed hiding alert id:" + id);
            throw new OperationFailedException("Alerte introuvable", e);
        }
    }

    @Override
    public List<Alerte> getList() {
        log.info("Service- BonEnginServiceImpl Calling getList");
        try {
            return alerteRepo.findByEtatOrderByCreatedAt(true);
        } catch (Exception e) {
            log.debug("Failed retrieving list of alerts");
			throw new OperationFailedException("Operation échouée", e);
		}
	}
}