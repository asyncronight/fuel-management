package me.kadarh.mecaworks.gazoil.service.impl.bons;

import me.kadarh.mecaworks.gazoil.domain.bons.BonEngin;
import me.kadarh.mecaworks.gazoil.domain.others.*;
import me.kadarh.mecaworks.gazoil.service.interfaces.bons.BonEnginService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kadarH
 */

public class BonEnginServiceImpl implements BonEnginService {
    @Override
    public BonEngin add(BonEngin bonEngin) {
        return null;
    }

    @Override
    public BonEngin update(BonEngin bonEngin) {
        return null;
    }

    @Override
    public BonEngin getBon(Long id) {
        return null;
    }

    @Override
    public List<BonEngin> bonList() {
        return null;
    }

    @Override
    public List<BonEngin> bonList(Famille famille, SousFamille sousFamille, Engin engin, Groupe groupe, Chantier chantier, LocalDate date1, LocalDate date2) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
