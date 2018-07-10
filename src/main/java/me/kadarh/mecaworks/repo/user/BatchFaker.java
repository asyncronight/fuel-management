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
            for (int i = 1; i < 7; i++) {
                chantierBatch = new ChantierBatch(i,
                        2018,
                        i == 1 || i == 3 || i == 5 ? 800000 + 100L * i : 550000 + 2510 / i + 1000L * i,
                        i == 2 || i == 3 || i == 5 ? 550000 + 1000L * i : 250000 + 1000L * i,
                        i == 1 || i == 4 || i == 5 ? 500000 + 210000 + 10000L * i : 500000 + 350000 + 10000L * i,
                        i == 1 || i == 3 || i == 5 ? 400000 + 210000 + 10000L * i : 400000 + 90000 + 10000L * i,
                        i == 2 || i == 1 || i == 6 ? 900000 + 1L : 400000 + 10000 + 1L,
                        8f,
                        i == 2 || i == 1 ? 759800 + 10L * i : 650700 + 10000 + 10L * i,
                        i == 2 || i == 1 ? 80000 + 9000 + 1L : 50000 + 2000 + 1L,
                        c);
                chantierBatchRepo.save(chantierBatch);
            }
            for (int i = 7; i <= 12; i++) {
                chantierBatch = new ChantierBatch(i,
                        2017,
                        i == 7 || i == 9 ? 700000 + 100L * i : 450000 + 2510 / i + 100L * i,
                        i == 9 || i == 8 || i == 11 || i == 10 || i == 12 ? 350000 + 1000L * i : 550000 + 1000L * i,
                        i == 8 || i == 10 || i == 11 ? 500000 + 210000 + 1L : 500000 + 350000 + 1L,
                        i == 7 || i == 8 || i == 12 ? 300000 + 210000 + 1L : 400000 + 90000 + 1L,
                        i == 7 || i == 9 ? 900000 + 1L : 400000 + 10000 + 1L,
                        8.5f,
                        i == 7 || i == 8 ? 759800 + 10L * i : 650700 + 10000 + 10L * i,
                        i == 9 || i == 8 || i == 11 ? 100000 + 9000 + 100L * i : 150000 + 9000 + 100L * i,
                        c);
                chantierBatchRepo.save(chantierBatch);
            }
        }
        log.info("Chantier_batch inserted");
    }
}
