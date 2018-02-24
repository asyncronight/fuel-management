package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Marque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MarqueService {

    Marque add(Marque marque);

    Marque update(Marque marque);

    Page<Marque> marqueList(Pageable pageable, String search);

    Marque get(Long id);

    void delete(Long id);

	List<Marque> list();
}
