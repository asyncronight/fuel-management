package me.kadarh.mecaworks.gazoil.service.impl;

import me.kadarh.mecaworks.gazoil.domain.alertes.AlerteEngin;
import me.kadarh.mecaworks.gazoil.domain.others.*;
import me.kadarh.mecaworks.gazoil.service.interfaces.AlerteService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kadarH
 */

public class AlerteServiceImpl implements AlerteService {
    @Override
    public AlerteEngin add(AlerteEngin alerteEngin) {
        return null;
    }

    @Override
    public AlerteEngin update(AlerteEngin alerteEngin) {
        return null;
    }

    @Override
    public List<AlerteEngin> alerteList() {
        return null;
    }

    @Override
    public List<AlerteEngin> alerteList(Chantier chantier, Engin engin, Groupe groupe, Famille famille, SousFamille sousFamille, LocalDate date1, LocalDate date2) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
