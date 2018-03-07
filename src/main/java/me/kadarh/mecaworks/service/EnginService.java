package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Engin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnginService {

	Engin add(Engin engin);

	Engin update(Engin engin);

    Engin get(Long id);

    Page<Engin> enginList(Pageable pageable, String search);

	void delete(Long id);

    List<Engin> getList();
}
