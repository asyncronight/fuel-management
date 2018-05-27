package me.kadarh.mecaworks.service.impl.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.domain.user.Dashbord;
import me.kadarh.mecaworks.domain.user.Quantite;
import me.kadarh.mecaworks.repo.user.ChantierBatchRepo;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import me.kadarh.mecaworks.service.exceptions.ResourceNotFoundException;
import me.kadarh.mecaworks.service.user.DashbordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class DashbordServiceImpl implements DashbordService {

    private ChantierBatchRepo chantierBatchRepo;
    private UserCalculService userCalculService;

    public DashbordServiceImpl(ChantierBatchRepo chantierBatchRepo, UserCalculService userCalculService) {
        this.chantierBatchRepo = chantierBatchRepo;
        this.userCalculService = userCalculService;
    }

    @Override
    public Dashbord getDashbord(int mois, int year) {
        log.info("calling method getDashbord(month,year) in DashbordServiceImpl -- Filling the Dashbord Object .. ");
        return addThisMonthToDashbord(getDashbordFromBatch(mois, year), mois, year);
    }

    private Dashbord addThisMonthToDashbord(Dashbord dashbord, int mois, int year) {
        log.info("calling method addThisMonthToDashbord() in DashbordServiceImpl -- ");
        log.info("--> Add data for this month ");
        try {
            if (mois == LocalDate.now().getMonthValue() && year == LocalDate.now().getYear())
                dashbord.setChantierBatch(userCalculService.getListChantierWithQuantities(mois, year));
            else
                dashbord.setChantierBatch(chantierBatchRepo.findAllByMoisAndAnnee(mois, year));
            dashbord.getQuantites().add(new Quantite(mois + "/" + year, dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getQuantite).sum(),
                    dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getQuantiteLocation).sum(),
                    dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getChargeLocataire).sum(),
                    dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getChargeLocataireExterne).sum(),
                    ((float) dashbord.getChantierBatch().stream().mapToDouble(ChantierBatch::getPrix).average().getAsDouble()),
                    dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getConsommationPrevue).sum()));
            log.info("--> Object Dashbored filled  ");
            return dashbord;
        } catch (NoSuchElementException e) {
            log.info("Operation failed : No element in ChantierBatch table -- ");
            throw new ResourceNotFoundException("Opération echouée, Il n'y a aucun element pour les mois precedents", e);
        } catch (Exception e) {
            log.info("Operation failed -- ");
            throw new OperationFailedException("Opération echouée, problème de la base", e);
        }
    }

    private Dashbord getDashbordFromBatch(int mois, int year) {
        log.info("calling method getDashbordFromBatch() in DashbordServiceImpl -- ");
        log.info("--> Add data for 12 last month [ one year ago ] ");
        try {
            List<Quantite> quantites = new ArrayList<>();
            List<ChantierBatch> chantierBatches;
            LocalDate d = LocalDate.of(year, mois, 1);
            for (int i = 12, month, yeaar; i >= 1; i--) {
                month = d.minusMonths(i).getMonthValue();
                yeaar = d.minusMonths(i).getYear();
                chantierBatches = chantierBatchRepo.findAllByMoisAndAnnee(month, yeaar);
                quantites.add(new Quantite(month + "/" + yeaar, chantierBatches.stream().mapToLong(ChantierBatch::getQuantite).sum(),
                        chantierBatches.stream().mapToLong(ChantierBatch::getQuantiteLocation).sum(),
                        chantierBatches.stream().mapToLong(ChantierBatch::getChargeLocataire).sum(),
                        chantierBatches.stream().mapToLong(ChantierBatch::getChargeLocataireExterne).sum(),
                        ((float) chantierBatches.stream().mapToDouble(ChantierBatch::getPrix).average().getAsDouble()),
                        chantierBatches.stream().mapToLong(ChantierBatch::getConsommationPrevue).sum()));
            }
            Dashbord dashbord = new Dashbord();
            dashbord.setQuantites(quantites);
            return dashbord;
        } catch (NoSuchElementException e) {
            log.info("Operation failed : No element in ChantierBatch table -- ");
            throw new ResourceNotFoundException("Opération echouée, Il n'y a aucun element pour les mois precedents", e);
        } catch (Exception e) {
            log.info("Operation failed -- ");
            throw new OperationFailedException("Opération echouée, problème de la base", e);
        }

    }
}