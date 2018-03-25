package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.alertes.Alerte;

import java.util.List;

public interface AlerteService {

	Alerte add(Alerte alerte);

	Alerte update(Alerte alerte);

	List<Alerte> alerteList();

	void delete(Long id);

}
