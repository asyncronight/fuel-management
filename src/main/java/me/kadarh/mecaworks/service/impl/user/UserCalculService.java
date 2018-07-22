package me.kadarh.mecaworks.service.impl.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.domain.user.Quantite;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.bons.BonFournisseurRepo;
import me.kadarh.mecaworks.repo.bons.BonLivraisonRepo;
import me.kadarh.mecaworks.repo.others.StockRepo;
import me.kadarh.mecaworks.service.EnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 20/05/18
 */

@Service
@Transactional
@Slf4j
public class UserCalculService {

    private final BonEnginRepo bonEnginRepo;
    private final EnginService enginService;
    private final BonFournisseurRepo bonFournisseurRepo;
    private final BonLivraisonRepo bonLivraisonRepo;
    private final StockRepo stockRepo;

    public UserCalculService(BonEnginRepo bonEnginRepo, EnginService enginService, BonFournisseurRepo bonFournisseurRepo, BonLivraisonRepo bonLivraisonRepo, StockRepo stockRepo) {
        this.bonEnginRepo = bonEnginRepo;
        this.enginService = enginService;
        this.bonFournisseurRepo = bonFournisseurRepo;
        this.bonLivraisonRepo = bonLivraisonRepo;
        this.stockRepo = stockRepo;
    }

    public List<ChantierBatch> getListChantierWithQuantities(int month, int year) {
        log.info("calling method getListChantierWithQuantities(month,year) in UserCalculService -- ");
        try {
            List<ChantierBatch> list = new ArrayList<>();
            ChantierBatch chantierBatch;
            LocalDate from = LocalDate.of(year, Month.of(month).getValue(), 1);
            LocalDate to = LocalDate.of(year, Month.of(month).plus(1).getValue(), 1);

            List<BonEngin> bonEngins = bonEnginRepo.findAllBetweenDates(from, to);
            List<BonLivraison> bonLivraisons = bonLivraisonRepo.findAllBetweenDates(from, to);
            List<BonFournisseur> bonFournisseurs = bonFournisseurRepo.findAllBetweenDates(from, to);

            Map<Chantier, Long> sum = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getQuantite)));
            Map<Chantier, Long> sum2 = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire() && bonEngin.getId() != null).collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getQuantite)));
            Map<Chantier, Long> chargeLocataire = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getChargeHoraire)));
            Map<Chantier, Long> chargeLocataireExterne = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getChargeHoraire)));
            Map<Chantier, Long> consommationPrevue = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getConsommationPrevu)));
            Map<Chantier, Double> prix = bonFournisseurs.stream().collect(Collectors.groupingBy(BonFournisseur::getChantier, Collectors.averagingDouble(BonFournisseur::getPrixUnitaire)));
            Map<Chantier, Long> quantiteAchetee = bonFournisseurs.stream().collect(Collectors.groupingBy(BonFournisseur::getChantier, Collectors.summingLong(BonFournisseur::getQuantite)));
            Map<Chantier, Long> quantiteFlotante = bonLivraisons.stream().collect(Collectors.groupingBy(BonLivraison::getChantierDepart, Collectors.summingLong(BonLivraison::getQuantite)));

            for (Map.Entry<Chantier, Long> entry : sum.entrySet()) {
                if (entry != null) {
                    //Fix in 0 if null
                    Long quantite = entry.getValue();
                    Long quantiteL = sum2.get(entry.getKey()) == null ? 0L : sum2.get(entry.getKey());
                    Long cl = chargeLocataire.get(entry.getKey()) == null ? 0L : chargeLocataire.get(entry.getKey());
                    Long clex = chargeLocataireExterne.get(entry.getKey()) == null ? 0L : chargeLocataireExterne.get(entry.getKey());
                    Long cp = consommationPrevue.get(entry.getKey()) == null ? 0L : consommationPrevue.get(entry.getKey());
                    Long qa = quantiteAchetee.get(entry.getKey()) == null ? 0L : quantiteAchetee.get(entry.getKey());
                    Long qf = quantiteFlotante.get(entry.getKey()) == null ? 0L : quantiteFlotante.get(entry.getKey());
                    Float p = prix.get(entry.getKey()) == null ? 0f : prix.get(entry.getKey()).floatValue();


                    chantierBatch = new ChantierBatch(month, year, quantite, quantiteL, cl, clex, cp, p, qa, qf, entry.getKey());
                    if (chantierBatch.getQuantiteLocation() == null) chantierBatch.setQuantiteLocation(0L);
                    if (chantierBatch.getQuantite() == null) chantierBatch.setQuantite(0L);
                    if (chantierBatch.getChargeLocataireExterne() == null) chantierBatch.setChargeLocataireExterne(0L);
                    if (chantierBatch.getChargeLocataire() == null) chantierBatch.setChargeLocataire(0L);
                    if (chantierBatch.getConsommationPrevue() == null) chantierBatch.setConsommationPrevue(0L);
                    if (chantierBatch.getGazoilAchete() == null) chantierBatch.setGazoilAchete(0L);
                    if (chantierBatch.getGazoilFlotant() == null) chantierBatch.setGazoilFlotant(0L);
                    list.add(chantierBatch);
                }
            }
            log.info("--> List of Chantier_Batch contains " + list.size() + " elements");
            return list;
        } catch (NoSuchElementException e) {
            log.info("Operation failed : No element in ChantierBatch table -- ");
            throw new ResourceNotFoundException("Opération echouée, Il n'y a aucun element pour les mois precedents", e);
        } catch (Exception e) {
            log.info("Operation failed -- ");
            throw new OperationFailedException("Opération echouée, problème de la base", e);
        }
    }

    public Quantite getMonthsWithQuantities(Chantier chantier, int month, int year) {
        log.info("calling method getListChantierWithQuantities(month,year) in UserCalculService -- ");
        LocalDate from = LocalDate.of(year, Month.of(month).getValue(), 1);
        LocalDate to = LocalDate.of(year, Month.of(month).plus(1).getValue(), 1);

        List<BonEngin> bonEngins = bonEnginRepo.findAllByChantier(chantier.getId(), from, to);
        List<BonLivraison> bonLivraisons = bonLivraisonRepo.findAllByChantier(chantier.getId(), from, to);
        List<BonFournisseur> bonFournisseurs = bonFournisseurRepo.findAllByChantier(chantier.getId(), from, to);
        Long quantiteTotal, quantiteLocation, chargeLocataireTotale, chargeLocataireExterne, consommationPrevue, gazoilAchetee, gazoilFlottant;

        quantiteTotal = bonEngins.stream().mapToLong(BonEngin::getQuantite).sum();
        quantiteLocation = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).mapToLong(BonEngin::getQuantite).sum();
        chargeLocataireTotale = bonEngins.stream().mapToLong(bonEngin -> bonEngin.getChargeHoraire()).sum();
        chargeLocataireExterne = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).mapToLong(BonEngin::getQuantite).sum();
        consommationPrevue = bonEngins.stream().mapToLong(BonEngin::getConsommationPrevu).sum();
        gazoilAchetee = bonFournisseurs.stream().mapToLong(BonFournisseur::getQuantite).sum();
        gazoilFlottant = bonLivraisons.stream().mapToLong(BonLivraison::getQuantite).sum();
        return new Quantite(month + "/" + year, quantiteTotal, quantiteLocation, chargeLocataireTotale, chargeLocataireExterne,
                0f, consommationPrevue, gazoilAchetee, gazoilFlottant);
    }

    public List<Quantite> getListDaysQuantities(Chantier chantier, int month, int year) {
        //Declaring variables
        List<Quantite> quantites = new ArrayList<>();
        LocalDate from = LocalDate.of(year, Month.of(month).getValue(), 1);
        LocalDate to = LocalDate.of(year, Month.of(month).plus(1).getValue(), 1);
        List<BonEngin> bonEngins = bonEnginRepo.findAllByChantier(chantier.getId(), from, to);
        List<BonLivraison> bonLivraisons = bonLivraisonRepo.findAllByChantier(chantier.getId(), from, to);
        List<BonFournisseur> bonFournisseurs = bonFournisseurRepo.findAllByChantier(chantier.getId(), from, to);
        Long quantiteTotal, quantiteLocation, chargeLocataireTotale, chargeLocataireExterne, consommationPrevue, gazoilAchetee, gazoilFlottant;
        String date;
        long days = ChronoUnit.DAYS.between(from, to);

        //Looping over days

        for (int i = 0; i < days; i++) {
            LocalDate localDate = LocalDate.of(year, month, i + 1);
            date = (i + 1) + "-" + month + "-" + year;
            quantiteTotal = bonEngins.stream().filter(be -> be.getDate().equals(localDate)).mapToLong(BonEngin::getQuantite).sum();
            quantiteLocation = bonEngins.stream().filter(be -> be.getDate().equals(localDate)).filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).mapToLong(BonEngin::getQuantite).sum();
            chargeLocataireTotale = bonEngins.stream().filter(be -> be.getDate().equals(localDate)).mapToLong(BonEngin::getNbrHeures).sum();
            chargeLocataireExterne = bonEngins.stream().filter(be -> be.getDate().equals(localDate)).mapToLong(BonEngin::getQuantite).sum();
            consommationPrevue = bonEngins.stream().filter(be -> be.getDate().equals(localDate)).mapToLong(BonEngin::getQuantite).sum();
            gazoilAchetee = bonFournisseurs.stream().filter(bf -> bf.getDate().equals(localDate)).mapToLong(BonFournisseur::getQuantite).sum();
            gazoilFlottant = bonLivraisons.stream().filter(bl -> bl.getDate().equals(localDate)).mapToLong(BonLivraison::getQuantite).sum();

            quantites.add(new Quantite(date, quantiteTotal, quantiteLocation, chargeLocataireTotale, chargeLocataireExterne,
                    0f, consommationPrevue, gazoilAchetee, gazoilFlottant));
        }
        return quantites;
    }

    public void updateListEnginWithConsommation(int monthValue, int year) {
        log.info("calling method getListEnginWithConsommation(month,year) in UserCalculService -- ");
        try {
            List<BonEngin> bonEngins = bonEnginRepo.findAllBetweenDates(LocalDate.of(year, Month.of(monthValue).getValue(), 1), LocalDate.of(year, Month.of(monthValue).plus(1).getValue(), 1));
            Map<Engin, Double> consommationPrevue = bonEngins.stream().filter(bonEngin -> bonEngin.getConsommationH() != 0 && bonEngin.getConsommationH() != null).collect(Collectors.groupingBy(BonEngin::getEngin, Collectors.averagingDouble(BonEngin::getConsommationPrevu)));
            for (Map.Entry<Engin, Double> entry : consommationPrevue.entrySet()) {
                if (entry != null) {
                    Engin engin = enginService.get(entry.getKey().getId());
                    engin.setConsommationMoyenne(entry.getValue().floatValue() > engin.getSousFamille().getConsommationHMax() ? entry.getValue().floatValue() : engin.getSousFamille().getConsommationHMax().floatValue());
                    engin = enginService.update(engin);
                    log.info("Engin with id =" + engin.getId() + " updated successfully");
                }
            }
        } catch (NoSuchElementException e) {
            log.info("Operation failed : No element in Engin table -- ");
            throw new ResourceNotFoundException("Opération echouée, Il n'y a aucun element pour cet engin", e);
        } catch (Exception e) {
            log.info("Operation failed -- ");
            throw new OperationFailedException("Opération echouée, problème de la base", e);
        }


    }

    public List<Stock> getListChantierStockDays(Chantier chantier, int month, int year) {
        LocalDate from = LocalDate.of(year, Month.of(month).getValue(), 1);
        LocalDate to = LocalDate.of(year, Month.of(month).plus(1).getValue(), 1);
        List<Stock> list = new ArrayList<>();
        List<Stock> stocks = stockRepo.findAllByChantier(chantier.getId(), from, to);
        Stock stock;
        Long stock1;
        long days = ChronoUnit.DAYS.between(from, to);

        for (int i = 0; i < days; i++) {
            stock = new Stock();
            LocalDate localDate = LocalDate.of(year, month, i + 1);
            stock1 = stocks.stream().filter(be -> be.getDate().equals(localDate)).mapToLong(Stock::getStockC).sum();
            stock.setStockC(stock1.intValue());
            stock.setChantier(chantier);
            stock.setDate(LocalDate.of(year, month, i + 1));
            list.add(stock);
        }
        return list;
    }
}

