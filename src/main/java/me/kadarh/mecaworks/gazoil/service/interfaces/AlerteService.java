package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.alertes.AlerteEngin;

import java.util.List;

public interface AlerteService {

    AlerteEngin add(AlerteEngin alerteEngin);

    AlerteEngin update(AlerteEngin alerteEngin);

    List<AlerteEngin> alerteList();

    void delete(Long id);

}
