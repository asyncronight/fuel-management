package me.kadarh.mecaworks.gazoil.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.gazoil.domain.others.SousFamille;
import me.kadarh.mecaworks.gazoil.repo.others.FamilleRepo;
import me.kadarh.mecaworks.gazoil.repo.others.SousFamilleRepo;
import me.kadarh.mecaworks.gazoil.service.SousFamilleService;
import me.kadarh.mecaworks.gazoil.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.gazoil.service.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class SousFamilleServiceImpl implements SousFamilleService {

    private SousFamilleRepo sousFamilleRepo;
    private FamilleRepo familleRepo;

    public SousFamilleServiceImpl(SousFamilleRepo sousFamilleRepo, FamilleRepo familleRepo) {
        this.sousFamilleRepo = sousFamilleRepo;
        this.familleRepo = familleRepo;
    }

    @Override
    public SousFamille add(SousFamille sousFamille) {
        log.info("Service= SousFamilleServiceImpl - calling methode add");
        try {
            sousFamille.setFamille(familleRepo.findByNom(sousFamille.getFamille().getNom()).get());
            return sousFamilleRepo.save(sousFamille);
        } catch (NoSuchElementException e) {
            log.debug("cannot find famille , failed operation");
            throw new ResourceNotFoundException("L'ajout de la Sous-famille a echouée,famille introuvable ", e);
        } catch (Exception e) {
            log.debug("cannot add SousFamille , failed operation");
            throw new OperationFailedException("L'ajout de la Sous-famille a echouée ", e);
        }
    }

    @Override
    public SousFamille update(SousFamille sousFamille) {
        log.info("Service= SousFamilleServiceImpl - calling methode update");
        try {
			SousFamille old = sousFamilleRepo.findById(sousFamille.getId()).get();
            if (sousFamille.getNom() != null) {
                old.setNom(sousFamille.getNom());
            }
            if (old.getCapaciteReservoir() != sousFamille.getCapaciteReservoir()) {
                old.setCapaciteReservoir(sousFamille.getCapaciteReservoir());
            }
            if (old.getConsommationKmMax() != sousFamille.getConsommationKmMax()) {
                old.setConsommationKmMax(sousFamille.getConsommationKmMax());
            }
            if (old.getConsommationLMax() != sousFamille.getConsommationLMax()) {
                old.setConsommationLMax(sousFamille.getConsommationLMax());
            }
            if (sousFamille.getTypeCompteur() != null) {
                old.setTypeCompteur(sousFamille.getTypeCompteur());
            }
            if (sousFamille.getFamille() != null) {
                old.setFamille(familleRepo.findByNom(sousFamille.getFamille().getNom()).get());
            }
            return sousFamilleRepo.save(sousFamille);

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
                sousFamille.setFamille(familleRepo.findByNom(search).get());
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
    public void delete(Long id) {
        log.info("Service= SousFamilleServiceImpl - calling methode update");
        try {
			sousFamilleRepo.deleteById(id);
        } catch (Exception e) {
            log.debug("cannot delete SousFamille , failed operation");
            throw new OperationFailedException("La suppression du SousFamille a echouée ", e);
        }
    }
}
