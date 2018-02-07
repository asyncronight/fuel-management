package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.*;

import java.time.LocalDate;
import java.util.List;

public interface AlerteService {

    Alerte add(Alerte alerte);
    Alerte update(Alerte alerte);
    void delete(Alerte alerte);
    List<Alerte> alerteList();
    List<Alerte> alerteList(TypeAlerte typeAlerte,Chantier chantier,Engin engin,Groupe groupe,LocalDate date1,LocalDate date2);

}
