package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Groupe;

import java.util.List;

public interface GroupeService {

    Groupe add(Groupe groupe);
    Groupe update(Groupe groupe);
    List<Groupe> groupesList();
    void delete(Long id);

}
