package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;

import java.time.LocalDate;
import java.util.List;

public interface BonEnginService {

	BonEngin add(BonEngin bonEngin);

    List<BonEngin> bonList(Long idEngin, LocalDate date1, LocalDate date2);


}
