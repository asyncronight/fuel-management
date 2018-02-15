package me.kadarh.mecaworks.gazoil.service;

import me.kadarh.mecaworks.gazoil.domain.others.Famille;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FamilleService {

   Famille add(Famille famille);

   Famille update(Famille famille);

    Page<Famille> familleList(Pageable pageable, String search);

    void delete(Long id);

}
