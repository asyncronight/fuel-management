package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Chantier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChantierService {

	Chantier add(Chantier chantier);

	Chantier update(Chantier chantier);

	Page<Chantier> chantierList(Pageable pageable, String search);

	void delete(Long id);


}
