package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FournisseurService {

	Fournisseur add(Fournisseur fournisseur);

	Fournisseur update(Fournisseur fournisseur);

	Page<Fournisseur> fournisseurList(Pageable pageable, String search);

	Fournisseur get(Long id);

	void delete(Long id);

    List<Fournisseur> getList();

}
