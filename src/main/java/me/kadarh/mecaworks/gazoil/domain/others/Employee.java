package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */
@Data
@Entity
public class Employee extends AbstractDomain {


    private String nom;

    public Employee(String nom) {
        this.nom = nom;
    }

    public Employee() {
    }
}
