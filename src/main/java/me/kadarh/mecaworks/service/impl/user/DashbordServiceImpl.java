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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class DashbordServiceImpl implements DashbordService {

    private ChantierBatchRepo chantierBatchRepo;

    public DashbordServiceImpl(ChantierBatchRepo chantierBatchRepo) {
        this.chantierBatchRepo = chantierBatchRepo;
    }

    @Override
    public Dashbord getDashbord(int mois, int year) {
        Dashbord dashbord = new Dashbord();
        Long somQ, somQL;
        int month, yeaar;
        Map<String, Quantite> map = new HashMap<>();
        List<ChantierBatch> chantierBatches = chantierBatchRepo.findAllByMoisAndAnnee(mois, year);
        dashbord.setChantierBatch(chantierBatches);
        LocalDate d = LocalDate.of(year, mois, 1);
        Quantite quantite = null;
        for (int i = 0; i < 12; i++) {
            month = d.minusMonths(i).getMonthValue();
            yeaar = d.minusMonths(i).getYear();
            somQ = 0L;
            somQL = 0L;
            chantierBatches = chantierBatchRepo.findAllByMoisAndAnnee(month, yeaar);
            for (ChantierBatch chantierBatch : chantierBatches) {
                somQ += chantierBatch.getQuantite();
                somQL += chantierBatch.getQuantiteLocation();
            }
            quantite = new Quantite(somQ, somQL);
            map.put(month + "/" + yeaar, quantite);
        }
        dashbord.setMap(map);
        return dashbord;
    }
}