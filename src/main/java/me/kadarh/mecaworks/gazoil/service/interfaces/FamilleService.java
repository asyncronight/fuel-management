package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Famille;

import java.util.List;

public interface FamilleService {

   Famille add(Famille famille);

   Famille update(Famille famille);
   List<Famille> familleList();
   void delete(Famille famille);

}
