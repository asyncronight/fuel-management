package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.Bon;
import me.kadarh.mecaworks.domain.Chantier;
import me.kadarh.mecaworks.domain.Engin;
import me.kadarh.mecaworks.domain.Groupe;

import java.time.LocalDate;
import java.util.List;

public interface BonService {

    Bon add(Bon bon);
    Bon getBon(Long id);
    List<Bon> bonList();
    List<Bon> bonList(Engin engin, Chantier chantier, LocalDate date1, LocalDate date2, Groupe group);
    Bon update(Bon bon);
    void delete(Long id);

}
