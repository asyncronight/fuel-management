package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Groupe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupeService {

	Groupe add(Groupe groupe);

	Groupe update(Groupe groupe);

	Page<Groupe> groupesList(Pageable pageable, String search);

	Groupe get(Long id);

	void delete(Long id);

    List<Groupe> list();
}
