package me.kadarh.mecaworks.service.user;

import me.kadarh.mecaworks.domain.user.DashbordChantier;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * @author salah3x
 * Created at 30/05/18
 */
public interface DashbordChantierService {

	DashbordChantier getDashbordChantier(Long idc, int mois, int annee);
}
