package me.kadarh.mecaworks.service.impl.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.bons.BonFournisseurRepo;
import me.kadarh.mecaworks.service.EnginService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
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

    public UserCalculService(BonEnginRepo bonEnginRepo, EnginService enginService, BonFournisseurRepo bonFournisseurRepo) {
        this.bonEnginRepo = bonEnginRepo;
        this.enginService = enginService;
        this.bonFournisseurRepo = bonFournisseurRepo;
    }

    public List<ChantierBatch> getListChantierWithQuantities(int month, int year) {
        log.info("calling method getListChantierWithQuantities(month,year) in UserCalculService -- ");
        try {
            List<ChantierBatch> list = new ArrayList<>();
            ChantierBatch chantierBatch;
            List<BonEngin> bonEngins = bonEnginRepo.findAllBetweenDates(LocalDate.of(year, Month.of(month).getValue(), 1), LocalDate.of(year, Month.of(month).plus(1).getValue(), 1));
            Map<Chantier, Long> sum = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getQuantite)));
            Map<Chantier, Long> sum2 = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getQuantite)));
            Map<Chantier, Long> chargeLocataire = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getChargeHoraire)));
            Map<Chantier, Long> chargeLocataireExterne = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getChargeHoraire)));
            Map<Chantier, Long> consommationPrevue = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getConsommationPrevu)));

            List<BonFournisseur> bonFournisseurs = bonFournisseurRepo.findAllBetweenDates(LocalDate.of(year, Month.of(month).getValue(), 1), LocalDate.of(year, Month.of(month).plus(1).getValue(), 1));
            Map<Chantier, Double> prix = bonFournisseurs.stream().collect(Collectors.groupingBy(BonFournisseur::getChantier, Collectors.averagingDouble(BonFournisseur::getPrixUnitaire)));
            for (Map.Entry<Chantier, Long> entry : sum.entrySet()) {
                chantierBatch = new ChantierBatch(month, year, entry.getValue(), sum2.get(entry.getKey()), chargeLocataire.get(entry.getKey()), chargeLocataireExterne.get(entry.getKey()), prix.get(entry.getKey()).floatValue(), consommationPrevue.get(entry.getKey()), entry.getKey());
                if (chantierBatch.getQuantiteLocation() == null) chantierBatch.setQuantiteLocation(0L);
                if (chantierBatch.getQuantite() == null) chantierBatch.setQuantite(0L);
                if (chantierBatch.getChargeLocataireExterne() == null) chantierBatch.setChargeLocataireExterne(0L);
                if (chantierBatch.getChargeLocataire() == null) chantierBatch.setChargeLocataire(0L);
                if (chantierBatch.getConsommationPrevue() == null) chantierBatch.setConsommationPrevue(0L);
                list.add(chantierBatch);
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

    public void updateListEnginWithConsommation(int monthValue, int year) {
        log.info("calling method getListEnginWithConsommation(month,year) in UserCalculService -- ");
        try {
            List<BonEngin> bonEngins = bonEnginRepo.findAllBetweenDates(LocalDate.of(year, Month.of(monthValue).getValue(), 1), LocalDate.of(year, Month.of(monthValue).plus(1).getValue(), 1));
            Map<Engin, Double> consommationPrevue = bonEngins.stream().filter(bonEngin -> bonEngin.getConsommationH() != 0 && bonEngin.getConsommationH() != null).collect(Collectors.groupingBy(BonEngin::getEngin, Collectors.averagingDouble(BonEngin::getConsommationPrevu)));
            for (Map.Entry<Engin, Double> entry : consommationPrevue.entrySet()) {
                Engin engin = enginService.get(entry.getKey().getId());
                engin.setConsommationMoyenne(entry.getValue().floatValue());
                engin = enginService.update(engin);
                log.info("Engin with id =" + engin.getId() + " updated successfully");
            }
        } catch (NoSuchElementException e) {
            log.info("Operation failed : No element in Engin table -- ");
            throw new ResourceNotFoundException("Opération echouée, Il n'y a aucun element pour cet engin", e);
        } catch (Exception e) {
            log.info("Operation failed -- ");
            throw new OperationFailedException("Opération echouée, problème de la base", e);
        }


    }
}

