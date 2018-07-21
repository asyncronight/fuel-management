package me.kadarh.mecaworks.service.impl.user;

import me.kadarh.mecaworks.domain.others.Stock;
import me.kadarh.mecaworks.domain.user.DashbordChantier;
import me.kadarh.mecaworks.domain.user.Quantite;
import me.kadarh.mecaworks.repo.others.StockRepo;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.user.DashbordChantierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private final StockRepo stockRepo;

	public DashbordChantierServiceImpl(UserCalculService userCalculService, ChantierService chantierService, StockRepo stockRepo) {
		this.userCalculService = userCalculService;
		this.chantierService = chantierService;
		this.stockRepo = stockRepo;
	}

	@Override
	public DashbordChantier getDashbordChantier(Long idc, int mois, int annee) {
		Long stock_c;
		Long ecartPlus;
		Long ecartMoins;
		LocalDate dateMaj;
		Optional<Stock> stock = stockRepo.findLastStockReel(idc);
		List<Quantite> quantites = new ArrayList<>();
		LocalDate d = LocalDate.of(annee, mois, 1);
        for (int i = 12, month, yeaar; i >= 0; i--) {
            month = d.minusMonths(i).getMonthValue();
			yeaar = d.minusMonths(i).getYear();
			quantites.add(userCalculService.getMonthsWithQuantities(chantierService.get(idc), month, yeaar));
		}
		if (stock.isPresent()) {
			stock_c = stock.get().getStockC().longValue();
			ecartPlus = stock.get().getEcart_plus().longValue();
			ecartMoins = stock.get().getEcart_moins().longValue();
			dateMaj = stock.get().getDate();
		} else {
			stock_c = chantierService.get(idc).getStock().longValue();
			ecartPlus = 0L;
			ecartMoins = 0L;
			dateMaj = LocalDate.now();
		}
		return new DashbordChantier(
				userCalculService.getListDaysQuantities(chantierService.get(idc), mois, annee),
				quantites, stock_c, ecartPlus, ecartMoins, dateMaj,
				chantierService.get(idc)
		);
	}
}
