package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Marque;
import me.kadarh.mecaworks.repo.others.MarqueRepo;
import me.kadarh.mecaworks.service.MarqueService;
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
public class MarqueServiceImpl implements MarqueService {

    private MarqueRepo marqueRepo;

    public MarqueServiceImpl(MarqueRepo marqueRepo) {
        this.marqueRepo = marqueRepo;
    }

    /**
     * @param marque to add
     * @return the Marque
     */
    @Override
    public Marque add(Marque marque) {
        log.info("Service= MarqueServiceImpl - calling methode add");
        try {
            return marqueRepo.save(marque);
        } catch (Exception e) {
            log.debug("cannot add marque , failed operation");
            throw new OperationFailedException("L'ajout de la marque a echouée ", e);
        }
    }

    /**
     * @param marque to add
     * @return the Marque
     */
    @Override
    public Marque update(Marque marque) {
        log.info("Service= MarqueServiceImpl - calling methode update");
        try {
            Marque old = marqueRepo.findById(marque.getId()).get();
            if (marque.getNom() != null) {
                old.setNom(marque.getNom());
            }
            return marqueRepo.save(old);

        } catch (Exception e) {
            log.debug("cannot update Marque , failed operation");
            throw new OperationFailedException("La modification de la marque a echouée ", e);
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
    public Page<Marque> marqueList(Pageable pageable, String search) {
        log.info("Service- MarqueServiceImpl Calling MarqueList with params :" + pageable + ", " + search);
        try {
            if (search.isEmpty()) {
                log.debug("fetching Marque page");
                return marqueRepo.findAll(pageable);
            } else {
                log.debug("Searching by :" + search);
                //creating example
                Marque marque = new Marque();
                marque.setNom(search);
                //creating matcher
                ExampleMatcher matcher = ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues();
                Example<Marque> example = Example.of(marque, matcher);
                log.debug("getting search results");
                return marqueRepo.findAll(example, pageable);
            }
        } catch (Exception e) {
            log.debug("Failed retrieving list of marques");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    @Override
    public Marque get(Long id) {
        log.info("Service-GroupeServiceImpl Calling getGroupe with params :" + id);
        try {
            return marqueRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find Marque with id = :" + id);
            throw new ResourceNotFoundException("Marque introuvable", e);
        } catch (Exception e) {
            log.info("Problem , cannot get Marque with id = :" + id);
            throw new OperationFailedException("Problème lors de la recherche de la Marque", e);
        }
    }

    /**
     * @param id of Marque to delete
     */
    @Override
    public void delete(Long id) {
        log.info("Service= MarqueServiceImpl - calling methode delete");
        try {
            marqueRepo.deleteById(id);
        } catch (Exception e) {
            log.debug("cannot delete Marque , failed operation");
            throw new OperationFailedException("La suppression de la Marque a echouée ", e);
        }
    }

    /**
     * @return List of marques in database
     */
    @Override
    public List<Marque> list() {
        log.info("Service= MarqueServiceImpl - calling methode list()");
        try {
            return marqueRepo.findAll();
        } catch (Exception e) {
            log.debug("cannot fetch list marques , failed operation");
            throw new OperationFailedException("La recherche des marques a echouée ", e);
        }
    }
}
