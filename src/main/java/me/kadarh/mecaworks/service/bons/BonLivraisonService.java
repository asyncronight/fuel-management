package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.Chantier;

import java.time.LocalDate;
import java.util.List;

public interface BonLivraisonService {

	BonLivraison add(BonLivraison bonLivraison);

	BonLivraison getBon(Long id);

	BonLivraison update(BonLivraison bonLivraison);

	List<BonLivraison> bonList();

	List<BonLivraison> bonList(Chantier chantierDepart, Chantier chantierArrivee, LocalDate date1, LocalDate date2);

	void delete(Long id);

}
