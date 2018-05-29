package me.kadarh.mecaworks.repo.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.service.ChantierService;
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

    private ChantierBatchRepo chantierBatchRepo;

    private ChantierService chantierService;

    public BatchFaker(ChantierBatchRepo chantierBatchRepo, ChantierService chantierService) {
        this.chantierBatchRepo = chantierBatchRepo;
        this.chantierService = chantierService;
    }

    public void insertBatchChantier() {
        log.info("Preparing to insert fake data ( chantier_batch ) ...");
        ChantierBatch chantierBatch;
        for (Chantier c : chantierService.getList()) {
            for (int i = 1; i < 5; i++) {
                chantierBatch = new ChantierBatch(i,
                        2018,
                        i == 1 || i == 3 ? 1050000 + 1L : 500000 + 25100 + 1L,
                        i == 2 || i == 3 ? 200560 + 1L : 250000 + 1L,
                        i == 1 || i == 4 ? 5000000 + 21000 + 1L : 5000000 + 35000 + 1L,
                        i == 1 || i == 3 ? 2000000 + 21000 + 1L : 3000000 + 9000 + 1L, 8f + i / 2,
                        i == 2 || i == 1 ? 500000 + 9000 + 1L : 500000 + 10000 + 1L, c);
                chantierBatchRepo.save(chantierBatch);
            }
            for (int i = 5; i <= 12; i++) {
                chantierBatch = new ChantierBatch(i,
                        2017,
                        i == 7 || i == 9 ? 1500000 + 1L : 580000 + 1L,
                        i == 6 || i == 8 || i == 10 ? 530000 + 1L + 4560 + 1L : 200000 + 1L,
                        i == 6 || i == 10 || i == 11 ? 5000000 + 2100000 + 1L : 5000000 + 3500000 + 1L,
                        i == 7 || i == 5 || i == 12 ? 3000000 + 2100000 + 1L : 4000000 + 900000 + 1L, 8.5f + i / 4,
                        i == 7 || i == 9 ? 1350000 + 1L : 590000 + 1L, c);
                chantierBatchRepo.save(chantierBatch);
            }
        }
        log.info("Chantier_batch inserted");
    }
}
