package me.kadarh.mecaworks.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.AlerteEngin;
import me.kadarh.mecaworks.service.AlerteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kadarH
 */

@Service
@Transactional
@Slf4j
public class AlerteServiceImpl implements AlerteService {

	// Todo @user kad : Fill this service

	@Override
	public AlerteEngin add(AlerteEngin alerteEngin) {
		return null;
	}

	@Override
	public AlerteEngin update(AlerteEngin alerteEngin) {
		return null;
	}

	@Override
	public List<AlerteEngin> alerteList() {
		return null;
	}

	@Override
	public void delete(Long id) {

	}
}
