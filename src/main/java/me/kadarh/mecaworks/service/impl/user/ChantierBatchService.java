package me.kadarh.mecaworks.service.impl.user;


import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.repo.user.ChantierBatchRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@Slf4j
public class ChantierBatchService {

    private final ChantierBatchRepo chantierBatchRepo;
    private final UserCalculService userCalculService;
    private final BatchHelper batchHelper;

    public ChantierBatchService(ChantierBatchRepo chantierBatchRepo, UserCalculService userCalculService, BatchHelper batchHelper) {
        this.chantierBatchRepo = chantierBatchRepo;
        this.userCalculService = userCalculService;
        this.batchHelper = batchHelper;
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void calcul() {
        log.info("calling method calcul() ChantierBatchService -- Insertion of batch .. ");
        chantierBatchRepo.saveAll(userCalculService.getListChantierWithQuantities(LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        log.info("Batch Inserted for the last month [ for each Chantier ]  ");
    }

    @Scheduled(fixedDelay = 30000)
    public void updateBatch() {
        log.info("calling method updateBatch() ChantierBatchService -- Insertion of batch .. ");
        chantierBatchRepo.saveAll(batchHelper.updateBatcheLastMonth());
        log.info("10 Batch Updated for the last month [ for each Chantier ]  ");
    }
}
