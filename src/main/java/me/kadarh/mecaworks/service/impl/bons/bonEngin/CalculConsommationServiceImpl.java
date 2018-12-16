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
 * @author kadarH
 */

@Service
@Slf4j
@Transactional
public class CalculConsommationServiceImpl {

    private final BonEnginRepo bonEnginRepo;

    public CalculConsommationServiceImpl(BonEnginRepo bonEnginRepo) {
        this.bonEnginRepo = bonEnginRepo;
    }

    public BonEngin calculConsommation(BonEngin bonEngin) {
        TypeCompteur typeCompteur = bonEngin.getEngin().getSousFamille().getTypeCompteur();
        BonEngin lastBon, lastBon2;
        long som_Q = 0;
        List<BonEngin> list = new ArrayList<>();
        if (typeCompteur.equals(TypeCompteur.H)) {
            lastBon = bonEnginRepo.findLastBonEnginH_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            if (lastBon != null) {
                list = bonEnginRepo.findAllBetweenLastBonAndCurrentBon_H(bonEngin.getEngin().getId(), lastBon.getCompteurAbsoluH());
                list.remove(lastBon);
                for (BonEngin b : list)
                    som_Q += b.getQuantite();
                som_Q += bonEngin.getQuantite();
                if (bonEngin.getCompteurAbsoluH() > lastBon.getCompteurAbsoluH())
                    bonEngin.setConsommationH((float) som_Q / (bonEngin.getCompteurAbsoluH() - lastBon.getCompteurAbsoluH()));
                else bonEngin.setConsommationH(0f);
            }
            if (bonEngin.getCompteurHenPanne() || (!list.isEmpty() && list.get(list.size() - 1).getCompteurHenPanne()))
                bonEngin.setConsommationH(0f);
        }
        if (typeCompteur.equals(TypeCompteur.KM)) {
            lastBon = bonEnginRepo.findLastBonEnginKm_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            if (lastBon != null) {
                list = bonEnginRepo.findAllBetweenLastBonAndCurrentBon_Km(bonEngin.getEngin().getId(), lastBon.getCompteurAbsoluKm());
                list.remove(lastBon);
                for (BonEngin b : list)
                    som_Q += b.getQuantite();
                som_Q += bonEngin.getQuantite();
                if (bonEngin.getCompteurAbsoluKm() > lastBon.getCompteurAbsoluKm())
                    bonEngin.setConsommationKm((float) som_Q * 100 / (bonEngin.getCompteurAbsoluKm() - lastBon.getCompteurAbsoluKm()));
                else bonEngin.setConsommationKm(0f);
            }
            if (bonEngin.getCompteurKmenPanne() || (!list.isEmpty() && list.get(list.size() - 1).getCompteurKmenPanne()))
                bonEngin.setConsommationKm(0f);
        }
        if (typeCompteur.equals(TypeCompteur.KM_H)) {
            long som_Q_2 = 0;
            lastBon = bonEnginRepo.findLastBonEnginKm_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            lastBon2 = bonEnginRepo.findLastBonEnginH_toConsommation(bonEngin.getEngin().getId(),bonEngin.getDate());
            if (lastBon != null) {
                list = bonEnginRepo.findAllBetweenLastBonAndCurrentBon_Km(bonEngin.getEngin().getId(), lastBon.getCompteurAbsoluKm());
                list.remove(lastBon);
                for (BonEngin b : list) {
                    if (b.getQuantite() != null)
                        som_Q += b.getQuantite();
                }
                som_Q += bonEngin.getQuantite();
                bonEngin.setConsommationKm((float) som_Q * 100 / (bonEngin.getCompteurAbsoluKm() - lastBon.getCompteurAbsoluKm()));
            }
            if (bonEngin.getCompteurHenPanne() || (!list.isEmpty() && list.get(list.size() - 1).getCompteurHenPanne()))
                bonEngin.setConsommationH(0f);
            if (lastBon2 != null) {
                list = bonEnginRepo.findAllBetweenLastBonAndCurrentBon_H(bonEngin.getEngin().getId(), lastBon2.getCompteurAbsoluH());
                list.remove(lastBon);
                for (BonEngin b : list) {
                    if (b.getQuantite() != null)
                        som_Q_2 += b.getQuantite();
                }
                som_Q_2 += bonEngin.getQuantite();
                bonEngin.setConsommationH((float) som_Q_2 / (bonEngin.getCompteurAbsoluH() - lastBon2.getCompteurAbsoluH()));
            }
            if (bonEngin.getCompteurKmenPanne() || (!list.isEmpty() && list.get(list.size() - 1).getCompteurKmenPanne()))
                bonEngin.setConsommationKm(0f);
        }
        return bonEngin;
    }

}