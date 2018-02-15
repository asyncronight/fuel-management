package me.kadarh.mecaworks.gazoil.service;

import me.kadarh.mecaworks.gazoil.domain.others.Groupe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupeService {

    Groupe add(Groupe groupe);
    Groupe update(Groupe groupe);

    Page<Groupe> groupesList(Pageable pageable, String search);
    void delete(Long id);

}
