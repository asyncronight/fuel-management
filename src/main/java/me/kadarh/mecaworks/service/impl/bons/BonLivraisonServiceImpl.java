package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.repo.bons.BonLivraisonRepo;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.EmployeService;
import me.kadarh.mecaworks.service.StockService;
import me.kadarh.mecaworks.service.bons.BonLivraisonService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class BonLivraisonServiceImpl implements BonLivraisonService {

    private StockService stockService;
    private BonLivraisonRepo bonLivraisonRepo;

	private final ChantierService chantierService;
	private final EmployeService employeService;

	public BonLivraisonServiceImpl(StockService stockService, BonLivraisonRepo bonLivraisonRepo, ChantierService chantierService, EmployeService employeService) {
		this.stockService = stockService;
		this.bonLivraisonRepo = bonLivraisonRepo;
		this.chantierService = chantierService;
		this.employeService = employeService;
	}

	@Override
	public BonLivraison add(BonLivraison bonLivraison) {
		try {
			bonLivraison = bonLivraisonRepo.save(fill(bonLivraison));
			insertStock_Livraison(bonLivraison);
            return bonLivraison;
		} catch (DataIntegrityViolationException e) {
			throw new OperationFailedException("Le code est unique , veuillez saisir un nouveau code", e);
		} catch (Exception e) {
			throw new OperationFailedException("L'ajout du bon a echoué , opération echoué", e);
		}
	}

	private BonLivraison fill(BonLivraison bonLivraison) {
		bonLivraison.setTransporteur(employeService.get(bonLivraison.getTransporteur().getId()));
		bonLivraison.setPompiste(employeService.get(bonLivraison.getPompiste().getId()));
		bonLivraison.setChantierDepart(chantierService.get(bonLivraison.getChantierDepart().getId()));
		bonLivraison.setChantierArrivee(chantierService.get(bonLivraison.getChantierArrivee().getId()));
		return bonLivraison;
	}

	@Override
	public void insertBonLivraison(BonEngin bonEngin) {
        BonLivraison bonLivraison = new BonLivraison();
        bonLivraison.setDate(bonEngin.getDate());
        bonLivraison.setChantierDepart(bonEngin.getChantierGazoil());
        bonLivraison.setChantierArrivee(bonEngin.getChantierTravail());
        bonLivraison.setCode(bonEngin.getCode() + "X" + bonEngin.getId());
        bonLivraison.setPompiste(bonEngin.getPompiste());
        bonLivraison.setTransporteur(bonEngin.getChauffeur());
        bonLivraison.setQuantite(bonEngin.getQuantite());
        add(bonLivraison);
    }

    public void insertStock_Livraison(BonLivraison bonLivraison) {
        //sortie Livraison
        Stock stock = new Stock();
		Chantier chantier = bonLivraison.getChantierDepart();
		stock.setChantier(chantier);
		stock.setDate(bonLivraison.getDate());
        stock.setSortieL(bonLivraison.getQuantite());
		if (stockService.getLastStock(chantier) != null)
			stock.setStockC(stockService.getLastStock(chantier).getStockC() - bonLivraison.getQuantite());
		else stock.setStockC(bonLivraison.getChantierDepart().getStock() - bonLivraison.getQuantite());

        //Entréé livraison
        Stock stock2 = new Stock();
		Chantier chantier1 = bonLivraison.getChantierArrivee();
		stock2.setChantier(chantier1);
		stock2.setDate(bonLivraison.getDate());
        stock2.setEntreeL(bonLivraison.getQuantite());
		if (stockService.getLastStock(chantier1) != null)
			stock2.setStockC(stockService.getLastStock(chantier1).getStockC() + bonLivraison.getQuantite());
		else stock2.setStockC(chantier1.getStock() + bonLivraison.getQuantite());
		stockService.add(stock);
        stockService.add(stock2);
    }

	@Override
	public BonLivraison getBon(Long id) {
		try {
			return bonLivraisonRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("L'element n'existe pas dans la base, opération echouée", e);
		} catch (Exception e) {
			throw new OperationFailedException("Opération echouée", e);
		}
	}

	@Override
	public BonLivraison update(BonLivraison bonLivraison) {
		try {
			BonLivraison old = bonLivraisonRepo.findById(bonLivraison.getId()).get();
			if (bonLivraison.getChantierDepart() != null)
				old.setChantierDepart(bonLivraison.getChantierDepart());
			if (bonLivraison.getChantierArrivee() != null)
				old.setChantierArrivee(bonLivraison.getChantierArrivee());
			if (bonLivraison.getCode() != null)
				old.setCode(bonLivraison.getCode());
			if (bonLivraison.getDate() != null)
				old.setDate(bonLivraison.getDate());
			if (bonLivraison.getPompiste() != null)
				old.setPompiste(bonLivraison.getPompiste());
			if (bonLivraison.getQuantite() != null)
				old.setTransporteur(bonLivraison.getTransporteur());
			return bonLivraisonRepo.save(old);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("Le bon n'existe pas , opération échouée");
		} catch (Exception e) {
			throw new OperationFailedException("Opération echouée", e);
		}
	}

	@Override
	public List<BonLivraison> bonList() {
		try {
			return bonLivraisonRepo.findAll();
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("La liste des bon est vide , opération échouée");
		} catch (Exception e) {
			throw new OperationFailedException("Opération echouée", e);
		}
	}

	/**
	 * Search by any attribute of BonLivraison if possible
	 *
	 * @param pageable
	 * @param search   keyword
	 * @return Page of results
	 */
	@Override
	public Page<BonLivraison> bonList(Pageable pageable, String search) {
		try {
			if (search.isEmpty()) {
				log.debug("fetching bonLivraison page");
				return bonLivraisonRepo.findAll(pageable);
			} else {
				log.debug("Searching by :" + search);
				//creating example
				//Searching by code, date, quantité, nom chantier, nom employe

				BonLivraison bonLivraison = new BonLivraison();
				Chantier chantier = new Chantier();
				chantier.setNom(search);
				bonLivraison.setChantierDepart(chantier);
				bonLivraison.setChantierArrivee(chantier);
				Employe employe = new Employe();
				employe.setNom(search);
				bonLivraison.setPompiste(employe);
				bonLivraison.setTransporteur(employe);

				bonLivraison.setCode(search);
				try {
					bonLivraison.setDate(LocalDate.parse(search, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				} catch (Exception e) {
					log.debug("Cannot search by date : keyword doesn't match date pattern");
				}
				try {
					bonLivraison.setQuantite(Integer.valueOf(search));
				} catch (Exception e) {
					log.debug("Cannot search by quantité : keyword is not a number");
				}

				//creating matcher
				ExampleMatcher matcher = ExampleMatcher.matchingAny()
						.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
						.withIgnoreCase()
						.withIgnoreNullValues();
				Example<BonLivraison> example = Example.of(bonLivraison, matcher);
				Pageable pageable1 = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "date"));
				log.debug("getting search results");
				return bonLivraisonRepo.findAll(example, pageable1);
			}
		} catch (Exception e) {
			log.debug("Failed retrieving list of bons de Lisvraison");
			throw new OperationFailedException("Operation échouée", e);
		}
	}

	@Override
	public void delete(Long id) {
		try {
			bonLivraisonRepo.delete(bonLivraisonRepo.findById(id).get());
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("Le bon n'existe pas , opération échouée");
		} catch (Exception e) {
			throw new OperationFailedException("Opération echouée", e);
		}
	}


}