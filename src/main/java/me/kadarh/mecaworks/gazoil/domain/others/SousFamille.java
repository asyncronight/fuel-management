package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"famille"})
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