package me.kadarh.mecaworks.service.impl.user;

import me.kadarh.mecaworks.domain.user.DashbordChantier;
import me.kadarh.mecaworks.domain.user.Quantite;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.user.DashbordChantierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/2/18 10:54 PM
 *
 * @author salah3x
 * @author kadarH
 */
@Service
@Transactional
public class DashbordChantierServiceImpl implements DashbordChantierService {

	private final UserCalculService userCalculService;
	private final ChantierService chantierService;

	public DashbordChantierServiceImpl(UserCalculService userCalculService, ChantierService chantierService) {
		this.userCalculService = userCalculService;
		this.chantierService = chantierService;
	}

	@Override
	public DashbordChantier getDashbordChantier(Long idc, int mois, int annee) {
		List<Quantite> quantites = new ArrayList<>();
		LocalDate d = LocalDate.of(annee, mois, 1);
		for (int i = 12, month, yeaar; i >= 1; i--) {
			month = d.minusMonths(i).getMonthValue();
			yeaar = d.minusMonths(i).getYear();
			quantites.add(userCalculService.getMonthsWithQuantities(chantierService.get(idc), month, yeaar));
		}

		return new DashbordChantier(
				userCalculService.getListDaysQuantities(chantierService.get(idc), mois, annee),
				quantites,
				chantierService.get(idc)
		);
	}
}
