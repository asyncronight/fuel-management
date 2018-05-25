package me.kadarh.mecaworks.service.impl.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.repo.user.ChantierBatchRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 25/05/18
 */

@Service
@Transactional
@Slf4j
public class BatchHelper {

    private final ChantierBatchRepo chantierBatchRepo;
    private final UserCalculService userCalculService;

    public BatchHelper(ChantierBatchRepo chantierBatchRepo, UserCalculService userCalculService) {
        this.chantierBatchRepo = chantierBatchRepo;
        this.userCalculService = userCalculService;
    }

    public List<ChantierBatch> updateBatcheLastMonth() {
        log.info("Calling method updateBatcheLastMonth() in BatchHelper ");
        int mois = LocalDate.now().getMonth().getValue() - 1;
        int year = LocalDate.now().getYear();
        List<ChantierBatch> chantierBatches = userCalculService.getListChantierWithQuantities(mois, year);
        for (ChantierBatch c : chantierBatches) {
            ChantierBatch old = chantierBatchRepo.findByMoisAndAnneeAndChantier(mois, year, c.getChantier()).get();
            if (c.getQuantite() != null && c.getQuantite().equals(old.getQuantite()))
                old.setQuantite(c.getQuantite());
            if (c.getQuantiteLocation() != null && c.getQuantiteLocation().equals(old.getQuantiteLocation()))
                old.setQuantiteLocation(c.getQuantiteLocation());
            if (c.getChargeLocataire() != null && c.getChargeLocataire().equals(old.getChargeLocataire()))
                old.setChargeLocataire(c.getChargeLocataire());
            if (c.getChargeLocataireExterne() != null && c.getChargeLocataireExterne().equals(old.getChargeLocataireExterne()))
                old.setChargeLocataireExterne(c.getChargeLocataireExterne());
            if (c.getConsommationPrevue() != null && c.getConsommationPrevue().equals(old.getConsommationPrevue()))
                old.setConsommationPrevue(c.getConsommationPrevue());
            chantierBatches.set(chantierBatches.indexOf(c), old);
        }
        return chantierBatches;
    }

}
