package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.Chantier;
import me.kadarh.mecaworks.domain.Engin;

import java.util.List;

public interface ChantierService {

    Chantier add(Chantier chantier);
    Chantier update(Chantier chantier);
    List<Chantier> chantiersList();
    void delete(Long id);

}
