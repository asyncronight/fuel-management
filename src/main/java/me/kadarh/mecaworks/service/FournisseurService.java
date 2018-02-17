package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FournisseurService {

	Fournisseur add(Fournisseur fournisseur);

	Fournisseur update(Fournisseur fournisseur);

	Page<Fournisseur> fournisseurList(Pageable pageable, String search);

	void delete(Long id);

}
