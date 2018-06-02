package me.kadarh.mecaworks.service.impl.user;

import me.kadarh.mecaworks.domain.user.DashbordChantier;
import me.kadarh.mecaworks.service.user.DashbordChantierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 6/2/18 10:54 PM
 *
 * @author salah3x
 */
@Service
@Transactional
public class DashbordChantierServiceImpl implements DashbordChantierService {

	@Override
	public DashbordChantier getDashbordChantier(Long idc, int mois, int annee) {
		//todo add impl
		return null;
	}
}
