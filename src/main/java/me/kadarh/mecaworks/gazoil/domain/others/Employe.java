package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Employe extends AbstractDomain {


    private String nom;

    public Employe(String nom) {
        this.nom = nom;
    }

    public Employe() {
    }
}
