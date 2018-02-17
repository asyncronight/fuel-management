package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.alertes.AlerteEngin;

import java.util.List;

public interface AlerteService {

	AlerteEngin add(AlerteEngin alerteEngin);

	AlerteEngin update(AlerteEngin alerteEngin);

	List<AlerteEngin> alerteList();

	void delete(Long id);

}
