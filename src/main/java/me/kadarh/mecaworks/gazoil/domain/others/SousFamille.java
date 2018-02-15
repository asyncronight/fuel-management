package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"famille"})
public class SousFamille extends AbstractDomain {

    private String nom;
    private TypeCompteur typeCompteur;
    private int consommationLMax;
    private int consommationKmMax;
    private int capaciteReservoir;

    @ManyToOne
    private Famille famille;
}