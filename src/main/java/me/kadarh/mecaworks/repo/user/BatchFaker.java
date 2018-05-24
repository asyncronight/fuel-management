package me.kadarh.mecaworks.repo.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.service.ChantierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 22/05/18
 */

@Slf4j
@Component
public class BatchFaker {

    @Autowired
    private ChantierBatchRepo chantierBatchRepo;

    @Autowired
    private ChantierService chantierService;

    public void insertBatchChantier() {
        log.info("Preparing to insert fake data ( chantier_batch ) ...");
        ChantierBatch chantierBatch;
        for (Chantier c : chantierService.getList()) {
            for (int i = 1; i < 5; i++) {
                chantierBatch = new ChantierBatch(i, 2018, 1050 + i * 2 + 1L, 453 * i / 2 + 1L, 20 + i * 2 + 1L, 11 * i / 2 + 1L, 11 * i / 2 + 1D, c);
                chantierBatchRepo.save(chantierBatch);
            }
            for (int i = 5; i <= 12; i++) {
                chantierBatch = new ChantierBatch(i, 2017, 1050 + i * 2 + 1L, 453 * i / 2 + 1L, 20 + i * 2 + 1L, 11 * i / 2 + 1L, 11 * i / 2 + 1D, c);
                chantierBatchRepo.save(chantierBatch);
            }
        }
        log.info("Chantier_batch inserted");
    }
}
