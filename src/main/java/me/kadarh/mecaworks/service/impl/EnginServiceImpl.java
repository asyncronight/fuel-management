package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Groupe;
import me.kadarh.mecaworks.domain.others.SousFamille;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.repo.others.GroupeRepo;
import me.kadarh.mecaworks.repo.others.SousFamilleRepo;
import me.kadarh.mecaworks.service.EnginService;
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
public class EnginServiceImpl implements EnginService {

	private EnginRepo enginRepo;
	private SousFamilleRepo sousFamilleRepo;
	private GroupeRepo groupeRepo;

	/**
	 * @param engin to add
	 * @return the engin
	 */
	@Override
	public Engin add(Engin engin) {
		log.info("Service = EnginServiceImpl - calling methode add");
		try {
			Groupe groupe = groupeRepo.findByNom(engin.getGroupe().getNom()).get();
			engin.setGroupe(groupe);
			engin.setSousFamille(sousFamilleRepo.findByNom(engin.getSousFamille().getNom()).get());
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
			if (engin.getSousFamille().getNom() != null)
				old.setSousFamille(sousFamilleRepo.findByNom(engin.getSousFamille().getNom()).get());
			return enginRepo.save(engin);
		} catch (Exception e) {
			log.debug("cannot update engin , failed operation");
			throw new OperationFailedException("La modification de l'engin a echouée ", e);
		}
	}

	@Override
	public List<Engin> enginList() {
		return null;
	}

	@Override
	public List<Engin> enginList(SousFamille sousFamille, Groupe groupe) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}
}
