package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.Famille;
import me.kadarh.mecaworks.domain.SousFamille;

import java.util.List;

public interface SousFamilleService {

   SousFamille add(SousFamille sousFamille);
   List<SousFamille> sousFamilleList();
   List<SousFamille> sousFamilleList(Famille famille);
   void delete(SousFamille sousFamille);

}
