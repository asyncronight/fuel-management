package me.kadarh.mecaworks.gazoil.service;

import me.kadarh.mecaworks.gazoil.domain.others.SousFamille;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SousFamilleService {

   SousFamille add(SousFamille sousFamille);

   SousFamille update(SousFamille sousFamille);

    Page<SousFamille> sousFamilleList(Pageable pageable, String search);

   void delete(Long id);

}
