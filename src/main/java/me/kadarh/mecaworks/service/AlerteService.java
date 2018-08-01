package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.alertes.Alerte;

import java.util.List;

public interface AlerteService {

	Alerte add(Alerte alerte);

	void hideAlert(Long id);

	List<Alerte> getList();
}