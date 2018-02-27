package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClasseService {

    Classe add(Classe classe);

    Classe update(Classe classe);

    Page<Classe> classeList(Pageable pageable, String search);

    Classe get(Long id);

    void delete(Long id);

    List<Classe> list();

    Optional<Classe> findByNom(String search);
}
