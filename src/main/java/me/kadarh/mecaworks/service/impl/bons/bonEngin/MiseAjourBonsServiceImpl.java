package me.kadarh.mecaworks.service.impl.bons.bonEngin;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 12/16/18
 */
@Service
@Transactional
@Slf4j
public class MiseAjourBonsServiceImpl {

    private final CalculAbsoluServiceImpl calculAbsoluService;
    private final CalculConsommationServiceImpl calculConsommationService;
    private final BonEnginRepo bonEnginRepo;

    public MiseAjourBonsServiceImpl(CalculAbsoluServiceImpl calculAbsoluService, CalculConsommationServiceImpl calculConsommationService, BonEnginRepo bonEnginRepo) {
        this.calculAbsoluService = calculAbsoluService;
        this.calculConsommationService = calculConsommationService;
        this.bonEnginRepo = bonEnginRepo;
    }

    public BonEngin doMiseAjour(BonEngin bonEngin){
        log.info("Prepare to do mise à jour ..");
        bonEnginsToUpdate(bonEngin).forEach(this::miseAjourBonEngin);
        bonEnginRepo.saveAll(bonEnginsToUpdate(bonEngin));
        return bonEngin;
    }

    private List<BonEngin> bonEnginsToUpdate(BonEngin bonEngin){
        log.info("Get List of bons to update :");
        List<BonEngin> bonEngins = new ArrayList<>();
        BonEngin lastBonPlein ;
        TypeCompteur typeCompteur = bonEngin.getEngin().getSousFamille().getTypeCompteur();
        if(typeCompteur.equals(TypeCompteur.H)) {
            lastBonPlein = bonEnginRepo.findLastBonEnginH_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            if(lastBonPlein != null)
                bonEngins = bonEnginRepo.findListBonEnginH_toConsommation(
                    bonEngin.getEngin().getId(), lastBonPlein.getCompteurAbsoluH());
        }
        else if (typeCompteur.equals(TypeCompteur.KM)) {
            lastBonPlein = bonEnginRepo.findLastBonEnginKm_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            bonEngins = bonEnginRepo.findListBonEnginKm_toConsommation(
                    bonEngin.getEngin().getId(), lastBonPlein.getCompteurAbsoluKm());
        }
        else if(typeCompteur.equals(TypeCompteur.KM_H)){
            lastBonPlein = bonEnginRepo.findLastBonEnginH_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            bonEngins = bonEnginRepo.findListBonEnginH_toConsommation(
                    bonEngin.getEngin().getId(),lastBonPlein.getCompteurAbsoluH());
            lastBonPlein = bonEnginRepo.findLastBonEnginKm_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            bonEngins.addAll(bonEnginRepo.findListBonEnginKm_toConsommation(
                    bonEngin.getEngin().getId(),lastBonPlein.getCompteurAbsoluH()));
        }
        log.info("List bons to update :"+bonEngins.size());
        return bonEngins;
    }

    private BonEngin miseAjourBonEngin(BonEngin bonEngin){
        bonEngin = calculAbsoluService.fillBon(bonEngin);
        if(bonEngin.getPlein())
            bonEngin = calculConsommationService.calculConsommation(bonEngin);
        else{
            bonEngin.setConsommationH(0f);
            bonEngin.setConsommationKm(0f);
        }
        return bonEngin;
    }


}
