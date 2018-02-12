package me.kadarh.mecaworks.gazoil.service;

import me.kadarh.mecaworks.gazoil.domain.others.Fournisseur;

import java.util.List;

public interface FournisseurService {

    Fournisseur add(Fournisseur fournisseur);

    Fournisseur update(Fournisseur fournisseur);

    List<Fournisseur> fournisseurList();

    void delete(Long id);

}
