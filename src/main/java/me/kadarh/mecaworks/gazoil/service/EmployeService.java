package me.kadarh.mecaworks.gazoil.service;

import me.kadarh.mecaworks.gazoil.domain.others.Employee;

import java.util.List;

public interface EmployeService {

    Employee add(Employee employee);

    Employee update(Employee employee);

    List<Employee> employeeList();

    void delete(Long id);

}
