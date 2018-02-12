package me.kadarh.mecaworks.gazoil.service;

import me.kadarh.mecaworks.gazoil.domain.alertes.AlerteEngin;
import me.kadarh.mecaworks.gazoil.domain.others.*;

import java.time.LocalDate;
import java.util.List;

public interface AlerteService {

    AlerteEngin add(AlerteEngin alerteEngin);

    AlerteEngin update(AlerteEngin alerteEngin);

    List<AlerteEngin> alerteList();

    List<AlerteEngin> alerteList(Chantier chantier, Engin engin, Groupe groupe, Famille famille, SousFamille sousFamille, LocalDate date1, LocalDate date2);

    void delete(Long id);

}
