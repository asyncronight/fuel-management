package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Chantier;

import java.util.List;

public interface ChantierService {

    Chantier add(Chantier chantier);
    Chantier update(Chantier chantier);
    List<Chantier> chantiersList();
    void delete(Long id);

}
