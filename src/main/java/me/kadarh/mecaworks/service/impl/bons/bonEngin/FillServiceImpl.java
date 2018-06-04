package me.kadarh.mecaworks.service.impl.bons.bonEngin;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.EmployeService;
import me.kadarh.mecaworks.service.EnginService;
import me.kadarh.mecaworks.service.FournisseurService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 01/06/18
 */

@Service
@Transactional
@Slf4j
public class FillServiceImpl {

    private final EnginService enginService;
    private final EmployeService employeService;
    private final ChantierService chantierService;
    private final FournisseurService fournisseurService;
    private final PersistServiceImpl persistService;

    public FillServiceImpl(EnginService enginService, EmployeService employeService, ChantierService chantierService, FournisseurService fournisseurService, PersistServiceImpl persistService) {
        this.enginService = enginService;
        this.employeService = employeService;
        this.chantierService = chantierService;
        this.fournisseurService = fournisseurService;
        this.persistService = persistService;
    }

    public BonEngin fillBonEnginPart1(BonEngin bon) {
        try {
            bon.setEngin(enginService.get(bon.getEngin().getId()));
            bon.setChauffeur(employeService.get(bon.getChauffeur().getId()));
            bon.setPompiste(employeService.get(bon.getPompiste().getId()));
            bon.setChantierTravail(chantierService.get(bon.getChantierTravail().getId()));
            bon.setChantierGazoil(chantierService.get(bon.getChantierGazoil().getId()));
            return bon;
        } catch (Exception e) {
            throw new OperationFailedException("Operation echouée", e);
        }
    }

    public BonEngin fillBonEnginPart2(BonEngin bon) {
        try {
            bon.setConsommationPrevu(bon.getEngin().getConsommationMoyenne().longValue() * bon.getNbrHeures());
            bon.setChargeHoraire(bon.getNbrHeures() * bon.getEngin().getPrixLocationJournalier().longValue() / bon.getEngin().getObjectif());
            log.info("Compteur Absolu H = " + bon.getCompteurAbsoluH());
            log.info("Compteur Absolu Km = " + bon.getCompteurAbsoluKm());
            log.info("Nombre Heure travaillé  = " + bon.getNbrHeures());
            log.info("Bon has been filled correctly");
            return bon;
        } catch (Exception e) {
            throw new OperationFailedException("Operation echouée", e);
        }
    }


}
