package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Famille;
import me.kadarh.mecaworks.gazoil.domain.others.SousFamille;

import java.util.List;

public interface SousFamilleService {

   SousFamille add(SousFamille sousFamille);

   SousFamille update(SousFamille sousFamille);
   List<SousFamille> sousFamilleList();
   List<SousFamille> sousFamilleList(Famille famille);

   void delete(Long id);

}
