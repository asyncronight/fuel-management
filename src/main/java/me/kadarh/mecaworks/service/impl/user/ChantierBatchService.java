package me.kadarh.mecaworks.service.impl.user;


import me.kadarh.mecaworks.repo.user.ChantierBatchRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ChantierBatchService {

    private final ChantierBatchRepo chantierBatchRepo;
    private final UserCalculService userCalculService;

    public ChantierBatchService(ChantierBatchRepo chantierBatchRepo, UserCalculService userCalculService) {
        this.chantierBatchRepo = chantierBatchRepo;
        this.userCalculService = userCalculService;
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void calcul() {
        chantierBatchRepo.saveAll(userCalculService.getListChantierWithQuantities(LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
    }

}
