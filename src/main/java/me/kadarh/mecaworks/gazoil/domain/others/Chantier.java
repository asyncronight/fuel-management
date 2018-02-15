package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Chantier extends AbstractDomain {

    private String nom;
    private String adresse;
    private int stock;

}
