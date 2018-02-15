package me.kadarh.mecaworks.gazoil.service.bons;

import me.kadarh.mecaworks.gazoil.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Fournisseur;

import java.time.LocalDate;
import java.util.List;

public interface BonFournisseurService {

    BonFournisseur add(BonFournisseur bonFournisseur);

    BonFournisseur getBon(Long id);

    BonFournisseur update(BonFournisseur bonFournisseur);

    List<BonFournisseur> bonList();

    List<BonFournisseur> bonList(Chantier chantier, Fournisseur fournisseur, LocalDate date1, LocalDate date2);

    void delete(Long id);

}
