package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChantierService {

    Chantier add(Chantier chantier);

    Chantier update(Chantier chantier);

    Page<Chantier> chantierList(Pageable pageable, String search);
    void delete(Long id);


}
