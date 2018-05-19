package me.kadarh.mecaworks.service.impl.user;


import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.user.ChantierBatchRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChantierBatchService {

    private final BonEnginRepo bonEnginRepo;
    private final ChantierBatchRepo chantierBatchRepo;

    public ChantierBatchService(BonEnginRepo bonEnginRepo, ChantierBatchRepo chantierBatchRepo) {
        this.bonEnginRepo = bonEnginRepo;
        this.chantierBatchRepo = chantierBatchRepo;
    }

    @Scheduled(cron = "0 0 1 10 * ?")
    public void calcul() {
        List<ChantierBatch> list = chantierBatchRepo.saveAll(getListChantierWithQuantities(LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        System.out.println("ok");
        list.stream().forEach(System.out::println);
    }

    public List<ChantierBatch> getListChantierWithQuantities(int month, int year) {
        List<ChantierBatch> list = new ArrayList<>();
        ChantierBatch chantierBatch;
        List<BonEngin> bonEngins = bonEnginRepo.findAllBetweenDates(LocalDate.of(year, Month.of(month), 1), LocalDate.of(year, Month.of(month).plus(1).getValue(), 1));
        Map<Chantier, Long> sum = bonEngins.stream().collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getQuantite)));
        Map<Chantier, Long> sum2 = bonEngins.stream().filter(bonEngin -> bonEngin.getEngin().getGroupe().getLocataire()).collect(Collectors.groupingBy(BonEngin::getChantierTravail, Collectors.summingLong(BonEngin::getQuantite)));
        for (Map.Entry<Chantier, Long> entry : sum.entrySet()) {
            chantierBatch = new ChantierBatch(month, year, entry.getValue(), sum2.get(entry.getKey()), entry.getKey());
            if (chantierBatch.getQuantiteLocation() == null) chantierBatch.setQuantiteLocation(0L);
            list.add(chantierBatch);
        }
        return list;
    }
}
