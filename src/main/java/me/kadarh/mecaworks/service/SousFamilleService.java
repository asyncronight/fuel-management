package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.SousFamille;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SousFamilleService {

	SousFamille add(SousFamille sousFamille);

	SousFamille update(SousFamille sousFamille);

	Page<SousFamille> sousFamilleList(Pageable pageable, String search);

	SousFamille get(Long id);

	void delete(Long id);

}
