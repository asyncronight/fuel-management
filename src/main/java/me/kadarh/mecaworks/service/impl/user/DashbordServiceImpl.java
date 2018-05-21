package me.kadarh.mecaworks.service.impl.user;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.user.ChantierBatch;
import me.kadarh.mecaworks.domain.user.Dashbord;
import me.kadarh.mecaworks.domain.user.Quantite;
import me.kadarh.mecaworks.repo.user.ChantierBatchRepo;
import me.kadarh.mecaworks.service.user.DashbordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

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
        return addThisMonthToDashbord(getDashbordFromBatch(mois, year), mois, year);
    }

    private Dashbord addThisMonthToDashbord(Dashbord dashbord, int mois, int year) {
        if (mois == LocalDate.now().getMonthValue() && year == LocalDate.now().getYear())
            dashbord.setChantierBatch(userCalculService.getListChantierWithQuantities(mois, year));
        else
            dashbord.setChantierBatch(chantierBatchRepo.findAllByMoisAndAnnee(mois, year));
        dashbord.getMap().put(mois + "/" + year, new Quantite(dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getQuantite).sum(),
                dashbord.getChantierBatch().stream().mapToLong(ChantierBatch::getQuantiteLocation).sum()));
        return dashbord;
    }

    private Dashbord getDashbordFromBatch(int mois, int year) {
        Dashbord dashbord = new Dashbord();
        int month, yeaar;
        LinkedHashMap<String, Quantite> map = new LinkedHashMap<>();
        List<ChantierBatch> chantierBatches = chantierBatchRepo.findAllByMoisAndAnnee(mois, year);
        dashbord.setChantierBatch(chantierBatches);
        LocalDate d = LocalDate.of(year, mois, 1);
        for (int i = 12; i >= 1; i--) {
            month = d.minusMonths(i).getMonthValue();
            yeaar = d.minusMonths(i).getYear();
            chantierBatches = chantierBatchRepo.findAllByMoisAndAnnee(month, yeaar);
            map.put(month + "/" + yeaar, new Quantite(chantierBatches.stream().mapToLong(ChantierBatch::getQuantite).sum(),
                    chantierBatches.stream().mapToLong(ChantierBatch::getQuantiteLocation).sum()));
        }
        dashbord.setMap(map);
        return dashbord;
    }
}