package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.Groupe;

import java.util.List;

public interface GroupeService {

    Groupe add(Groupe groupe);
    Groupe update(Groupe groupe);
    List<Groupe> groupesList();
    void delete(Long id);

}
