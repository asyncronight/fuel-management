package me.kadarh.mecaworks.service.impl.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.service.bons.BonEnginService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kadarH
 */

public class BonEnginServiceImpl implements BonEnginService {
	@Override
	public BonEngin add(BonEngin bonEngin) {
		return null;
	}

	@Override
	public BonEngin update(BonEngin bonEngin) {
		return null;
	}

	@Override
	public BonEngin getBon(Long id) {
		return null;
	}

	@Override
	public List<BonEngin> bonList() {
		return null;
	}

	@Override
	public List<BonEngin> bonList(Famille famille, SousFamille sousFamille, Engin engin, Groupe groupe, Chantier chantier, LocalDate date1, LocalDate date2) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}
}
