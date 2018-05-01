package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BonLivraisonService {

	BonLivraison add(BonLivraison bonLivraison);

    void insertBonLivraison(BonEngin bonEngin);

	BonLivraison getBon(Long id);

	BonLivraison update(BonLivraison bonLivraison);

	List<BonLivraison> bonList();

	Page<BonLivraison> bonList(Pageable pageable, String search);

	void delete(Long id);

}
