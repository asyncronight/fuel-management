package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Famille;
import me.kadarh.mecaworks.domain.others.Marque;
import me.kadarh.mecaworks.domain.others.SousFamille;
import me.kadarh.mecaworks.repo.others.SousFamilleRepo;
import me.kadarh.mecaworks.service.FamilleService;
import me.kadarh.mecaworks.service.MarqueService;
import me.kadarh.mecaworks.service.SousFamilleService;
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
@Slf4j
@Service
@Transactional
public class SousFamilleServiceImpl implements SousFamilleService {

	private final SousFamilleRepo sousFamilleRepo;
	private final FamilleService familleService;
	private final MarqueService marqueService;

	public SousFamilleServiceImpl(SousFamilleRepo sousFamilleRepo, FamilleService familleService, MarqueService marqueService) {
		this.sousFamilleRepo = sousFamilleRepo;
		this.familleService = familleService;
		this.marqueService = marqueService;
	}


	@Override
	public SousFamille add(SousFamille sousFamille) {
		log.info("Service= SousFamilleServiceImpl - calling methode add");
		try {
			sousFamille.setFamille(familleService.get(sousFamille.getFamille().getId()));
			sousFamille.setMarque(marqueService.get(sousFamille.getMarque().getId()));
			return sousFamilleRepo.save(sousFamille);
		} catch (ResourceNotFoundException e) {
			log.debug("cannot find famille or marque , failed operation");
			throw new OperationFailedException("L'ajout de la Sous-famille a echouée,famille ou marque introuvable ", e);
		} catch (Exception e) {
			log.debug("cannot add SousFamille , failed operation");
			throw new OperationFailedException("L'ajout de la Sous-famille a echouée ", e);
		}
	}

	@Override
	public SousFamille update(SousFamille sousFamille) {
		log.info("Service= SousFamilleServiceImpl - calling methode update");
		try {
			SousFamille old = get(sousFamille.getId());
			if (sousFamille.getNom() != null) {
				old.setNom(sousFamille.getNom());
			}
			if (sousFamille.getCapaciteReservoir() != null) {
				old.setCapaciteReservoir(sousFamille.getCapaciteReservoir());
			}
			if (sousFamille.getConsommationKmMax() != null) {
				old.setConsommationKmMax(sousFamille.getConsommationKmMax());
			}
			if (sousFamille.getConsommationHMax() != null) {
				old.setConsommationHMax(sousFamille.getConsommationHMax());
			}
			if (sousFamille.getMarque() != null)
				old.setMarque(marqueService.get(sousFamille.getMarque().getId()));

			if (sousFamille.getTypeCompteur() != null) {
				old.setTypeCompteur(sousFamille.getTypeCompteur());
			}
			if (sousFamille.getFamille() != null) {
				old.setFamille(familleService.get(sousFamille.getFamille().getId()));
			}
			return sousFamilleRepo.save(old);

		} catch (ResourceNotFoundException e) {
			log.debug("cannot find famille or marque , failed operation");
			throw new OperationFailedException("La modification de la Sous-famille a echouée,famille ou marque introuvable ", e);
		} catch (Exception e) {
			log.debug("cannot update Sousfamille , failed operation");
			throw new OperationFailedException("La modification de la Sousfamille a echouée ", e);
		}
	}

	@Override
	public Page<SousFamille> sousFamilleList(Pageable pageable, String search) {
		log.info("Service- SousFamilleServiceImpl Calling sousFamilleList with params :" + pageable + ", " + search);
		try {
			if (search.isEmpty()) {
				log.debug("fetching sousFamille page");
				return sousFamilleRepo.findAll(pageable);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				SousFamille sousFamille = new SousFamille();
				sousFamille.setNom(search);

				Famille famille = new Famille();
				famille.setNom(search);
				Marque marque = new Marque();
				marque.setNom(search);

				sousFamille.setFamille(famille);
				sousFamille.setMarque(marque);
				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<SousFamille> example = Example.of(sousFamille, matcher);
				log.debug("getting search results");
				return sousFamilleRepo.findAll(example, pageable);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of SousFamilles");
			throw new OperationFailedException("Operation échouée", e);
		}
	}


	@Override
	public SousFamille get(Long id) {
		log.info("Service-SousFamilleServiceImpl Calling getSousFamille with params :" + id);
		try {
			return sousFamilleRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			log.info("Problem , cannot find SousFamille with id = :" + id);
			throw new ResourceNotFoundException("SousFamille introuvable", e);
		} catch (Exception e) {
			log.info("Problem , cannot get SousFamille with id = :" + id);
			throw new OperationFailedException("Problème lors de la recherche de la SousFamille", e);
		}
	}

	@Override
	public void delete(Long id) {
		log.info("Service= SousFamilleServiceImpl - calling methode update");
		try {
			sousFamilleRepo.deleteById(id);
		} catch (Exception e) {
			log.debug("cannot delete SousFamille , failed operation");
			throw new OperationFailedException("La suppression du SousFamille a echouée ", e);
		}
	}

	/**
	 * @return List of All SousFamilles in database
	 */
	@Override
	public List<SousFamille> list() {
		log.info("Service= SousFamilleServiceImpl - calling methode list");
		try {
			return sousFamilleRepo.findAll();
		} catch (Exception e) {
			log.debug("Failed retrieving list of SousFamilles");
			throw new OperationFailedException("Operation échouée", e);
		}
	}

}
