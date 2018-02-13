package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FournisseurService {

    Fournisseur add(Fournisseur fournisseur);

    Fournisseur update(Fournisseur fournisseur);

    Page<Fournisseur> fournisseurList(Pageable pageable, String search);

    void delete(Long id);

}
