package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Classe;
import me.kadarh.mecaworks.repo.others.ClasseRepo;
import me.kadarh.mecaworks.service.ClasseService;
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
import java.util.Optional;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class ClasseServiceImpl implements ClasseService {

    private ClasseRepo classeRepo;

    public ClasseServiceImpl(ClasseRepo classeRepo) {
        this.classeRepo = classeRepo;
    }

    /**
     * @param classe to add
     * @return the classe
     */
    @Override
    public Classe add(Classe classe) {
        log.info("Service= ClasseServiceImpl - calling methode add");
        try {
            return classeRepo.save(classe);
        } catch (Exception e) {
            log.debug("cannot add Classe , failed operation");
            throw new OperationFailedException("L'ajout de la Classe a echouée ", e);
        }
    }

    /**
     * @param classe to add
     * @return the Classe
     */
    @Override
    public Classe update(Classe classe) {
        log.info("Service= ClasseServiceImpl - calling methode update");
        try {
            Classe old = classeRepo.findById(classe.getId()).get();
            if (classe.getNom() != null) {
                old.setNom(classe.getNom());
            }
            return classeRepo.save(old);

        } catch (Exception e) {
            log.debug("cannot update Classe , failed operation");
            throw new OperationFailedException("La modification de la Classe a echouée ", e);
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
    public Page<Classe> classeList(Pageable pageable, String search) {
        log.info("Service- ClasseServiceImpl Calling ClasseList with params :" + pageable + ", " + search);
        try {
            if (search.isEmpty()) {
                log.debug("fetching Classe page");
                return classeRepo.findAll(pageable);
            } else {
                log.debug("Searching by :" + search);
                //creating example
                Classe classe = new Classe();
                classe.setNom(search);
                //creating matcher
                ExampleMatcher matcher = ExampleMatcher.matchingAny()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreCase()
                        .withIgnoreNullValues();
                Example<Classe> example = Example.of(classe, matcher);
                log.debug("getting search results");
                return classeRepo.findAll(example, pageable);
            }
        } catch (Exception e) {
            log.debug("Failed retrieving list of Classes");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    @Override
    public Classe get(Long id) {
        log.info("Service-ClasseServiceImpl Calling getClasse with params :" + id);
        try {
            return classeRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            log.info("Problem , cannot find Classe with id = :" + id);
            throw new ResourceNotFoundException("Classe introuvable", e);
        } catch (Exception e) {
            log.info("Problem , cannot get Classe with id = :" + id);
            throw new OperationFailedException("Problème lors de la recherche de la Classe", e);
        }
    }

    /**
     * @param id of Classe to delete
     */
    @Override
    public void delete(Long id) {
        log.info("Service= ClasseServiceImpl - calling methode delete");
        try {
            classeRepo.deleteById(id);
        } catch (Exception e) {
            log.debug("cannot delete Classe , failed operation");
            throw new OperationFailedException("La suppression de la Classe a echouée ", e);
        }
    }

    /**
     * @return List of All Classes in database
     */
    @Override
    public List<Classe> list() {
        log.info("Service= ClasseServiceImpl - calling methode list()");
        try {
            return classeRepo.findAll();
        } catch (Exception e) {
            log.debug("cannot fetch list classes , failed operation");
            throw new OperationFailedException("La recherche des classes a echouée ", e);
        }
    }

    /**
     * @param search , String param
     * @return Optional
     * the Classe object with Nom like search
     */
    @Override
    public Optional<Classe> findByNom(String search) {
        log.info("Service= ClasseServiceImpl - calling methode findByNom()");
        try {
            return classeRepo.findByNom(search);
        } catch (Exception e) {
            log.debug("cannot find Classe , failed operation");
            throw new OperationFailedException("La recherche de la Classe a echouée ", e);
        }
    }
}
