package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */
@Data
@Entity
public class Employe extends AbstractDomain {


    private String nom;

    public Employe(String nom) {
        this.nom = nom;
    }

    public Employe() {
    }
}
