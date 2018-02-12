package me.kadarh.mecaworks.gazoil.service.impl.bons;

import me.kadarh.mecaworks.gazoil.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Fournisseur;
import me.kadarh.mecaworks.gazoil.service.interfaces.bons.BonFournisseurService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kadarH
 */

public class BonFournisseurServiceImpl implements BonFournisseurService {
    @Override
    public BonFournisseur add(BonFournisseur bonFournisseur) {
        return null;
    }

    @Override
    public BonFournisseur getBon(Long id) {
        return null;
    }

    @Override
    public BonFournisseur update(BonFournisseur bonFournisseur) {
        return null;
    }

    @Override
    public List<BonFournisseur> bonList() {
        return null;
    }

    @Override
    public List<BonFournisseur> bonList(Chantier chantier, Fournisseur fournisseur, LocalDate date1, LocalDate date2) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
