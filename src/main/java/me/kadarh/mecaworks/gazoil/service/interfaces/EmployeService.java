package me.kadarh.mecaworks.gazoil.service.interfaces;

import me.kadarh.mecaworks.gazoil.domain.others.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeService {

    Employe add(Employe employe);

    Employe update(Employe employe);

    Page<Employe> employesList(Pageable pageable, String search);

    void delete(Long id);
}
