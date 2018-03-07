package me.kadarh.mecaworks.service;

import me.kadarh.mecaworks.domain.others.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeService {

	Employe add(Employe employe);

	Employe update(Employe employe);

	Page<Employe> employesList(Pageable pageable, String search);

    Employe get(Long id);

	void delete(Long id);

    List<Employe> getList();
}
