package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.others.*;

import java.time.LocalDate;
import java.util.List;

public interface BonEnginService {

	BonEngin add(BonEngin bonEngin);

	BonEngin update(BonEngin bonEngin);

	BonEngin getBon(Long id);

	List<BonEngin> bonList();

	List<BonEngin> bonList(Famille famille, SousFamille sousFamille, Engin engin, Groupe groupe, Chantier chantier, LocalDate date1, LocalDate date2);

	void delete(Long id);

}
