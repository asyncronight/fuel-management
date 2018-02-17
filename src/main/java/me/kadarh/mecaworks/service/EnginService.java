package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Groupe;
import me.kadarh.mecaworks.domain.others.SousFamille;

import java.util.List;

public interface EnginService {

	Engin add(Engin engin);

	Engin update(Engin engin);

	List<Engin> enginList();

	List<Engin> enginList(SousFamille sousFamille, Groupe groupe);

	void delete(Long id);

}
