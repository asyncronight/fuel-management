package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.*;

import java.time.LocalDate;
import java.util.List;

public interface FamilleService {

   Famille add(Famille famille);
   List<Famille> familleList();
   void delete(Famille famille);

}
