package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BonFournisseurService {

	BonFournisseur add(BonFournisseur bonFournisseur);

	BonFournisseur getBon(Long id);

	BonFournisseur update(BonFournisseur bonFournisseur);

	List<BonFournisseur> bonList();

	Page<BonFournisseur> bonList(Pageable pageable, String search);

	void delete(Long id);

}
