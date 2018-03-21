package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Fournisseur;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.repo.bons.BonFournisseurRepo;
import me.kadarh.mecaworks.repo.others.StockRepo;
import me.kadarh.mecaworks.service.bons.BonFournisseurService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.*;
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
public class BonFournisseurServiceImpl implements BonFournisseurService {

    private BonFournisseurRepo bonFournisseurRepo;
    private StockRepo stockRepo;

    public BonFournisseurServiceImpl(BonFournisseurRepo bonFournisseurRepo, StockRepo stockRepo) {
        this.bonFournisseurRepo = bonFournisseurRepo;
        this.stockRepo = stockRepo;
    }

	@Override
	public BonFournisseur add(BonFournisseur bonFournisseur) {
        log.info("Service- BonFournisseurServiceImpl Calling add ");
        try {
            //create a stock object and fill it from bon fournisseur
            Stock stock = new Stock();
            stock.setDate(bonFournisseur.getDate());
            stock.setEntreeF(bonFournisseur.getQuantite());
            stock.setChantier(bonFournisseur.getChantier());

            //saving the stock
            stockRepo.save(stock);

            //saving the bon and return it
            return bonFournisseurRepo.save(bonFournisseur);
        } catch (Exception e) {
            throw new OperationFailedException("l'ajout du bon a été suspendu, opération echouée", e);
        }
    }

	@Override
	public BonFournisseur getBon(Long id) {
        log.info("Service- BonFournisseurServiceImpl Calling getBon with param id = " + id);
        try {
            return bonFournisseurRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("opération echouée , le bon n'existe pas ", e);
        } catch (Exception e) {
            throw new OperationFailedException("opération echouée , problème lors de l'extraction du bon ", e);
        }
    }

	@Override
	public BonFournisseur update(BonFournisseur bonFournisseur) {
        log.info("Service- BonFournisseurServiceImpl Calling update with param id = " + bonFournisseur.getId());
        try {
            BonFournisseur old = bonFournisseurRepo.findById(bonFournisseur.getId()).get();
            if (bonFournisseur.getCode() != null)
                old.setCode(bonFournisseur.getCode());
            if (bonFournisseur.getDate() != null)
                old.setDate(bonFournisseur.getDate());
            if (bonFournisseur.getPrixUnitaire() != null)
                old.setPrixUnitaire(bonFournisseur.getPrixUnitaire());
            if (bonFournisseur.getQuantite() != null)
                old.setQuantite(bonFournisseur.getQuantite());
            if (bonFournisseur.getChantier() != null)
                old.setChantier(bonFournisseur.getChantier());
            if (bonFournisseur.getFournisseur() != null)
                old.setFournisseur(bonFournisseur.getFournisseur());
            return bonFournisseurRepo.save(bonFournisseur);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("opération echouée , le bon n'existe pas ", e);
        } catch (Exception e) {
            throw new OperationFailedException("opération echouée , problème lors de l'extraction du bon ", e);
        }

	}

	@Override
	public List<BonFournisseur> bonList() {
        log.info("Service- BonFournisseurServiceImpl Calling bonList ");
        try {
            return bonFournisseurRepo.findAll();
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("opération échouée , la liste des bons est vide", e);
        } catch (Exception e) {
            throw new OperationFailedException("opération echouée , l'extraction de la liste des bon a echouée", e);
        }
    }

	@Override
    public Page<BonFournisseur> bonList(Pageable pageable, String search) {
        log.info("Service- BonFournisseurServiceImpl Calling bonList with params :" + pageable + ", " + search);
        try {
            if (search.isEmpty()) {
                log.debug("fetching bonEngins page");
                return bonFournisseurRepo.findAll(pageable);
            } else {
                log.debug("Searching by :" + search);
                //creating example
                //Searching by code bon , nom chantier, nom fournisseur

                BonFournisseur bonFournisseur = new BonFournisseur();
                Chantier chantier = new Chantier();
                chantier.setNom(search);
                Fournisseur fournisseur = new Fournisseur();
                fournisseur.setNom(search);
                bonFournisseur.setChantier(chantier);
                bonFournisseur.setFournisseur(fournisseur);
                bonFournisseur.setCode(search);

                //creating matcher
                ExampleMatcher matcher = ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues();
                Example<BonFournisseur> example = Example.of(bonFournisseur, matcher);
                Pageable pageable1 = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, bonFournisseur.getDate().toString()));
                log.debug("getting search results");
                return bonFournisseurRepo.findAll(example, pageable1);
            }
        } catch (Exception e) {
            log.debug("Failed retrieving list of employes");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

	@Override
	public void delete(Long id) {
        log.info("Service- BonFournisseurServiceImpl Calling delete with param id = " + id);
        try {
            bonFournisseurRepo.delete(bonFournisseurRepo.findById(id).get());
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("opération échouée , l'élement est introuvable", e);
        } catch (Exception e) {
            throw new OperationFailedException("opération echouée , la suppression du bon a echouée", e);
        }
    }
}