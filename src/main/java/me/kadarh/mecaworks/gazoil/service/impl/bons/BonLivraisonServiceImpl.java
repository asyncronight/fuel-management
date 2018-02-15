package me.kadarh.mecaworks.gazoil.service.impl.bons;

import me.kadarh.mecaworks.gazoil.domain.bons.BonLivraison;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.service.bons.BonLivraisonService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kadarH
 */

public class BonLivraisonServiceImpl implements BonLivraisonService {
    @Override
    public BonLivraison add(BonLivraison bonLivraison) {
        return null;
    }

    @Override
    public BonLivraison getBon(Long id) {
        return null;
    }

    @Override
    public BonLivraison update(BonLivraison bonLivraison) {
        return null;
    }

    @Override
    public List<BonLivraison> bonList() {
        return null;
    }

    @Override
    public List<BonLivraison> bonList(Chantier chantierDepart, Chantier chantierArrivee, LocalDate date1, LocalDate date2) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
