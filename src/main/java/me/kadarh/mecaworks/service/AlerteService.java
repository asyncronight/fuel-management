package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.alertes.Alerte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlerteService {

	Alerte add(Alerte alerte);

	Page<Alerte> getPage(Pageable pageable, String search);

}