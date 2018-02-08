package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"sousFamille","groupe"})
@EqualsAndHashCode(callSuper = true, exclude = {"sousFamille", "groupe"})
public class Engin extends AbstractDomain {

    @Column(unique = true)
    private String code;

    private String numeroSerie;

    private TypeCompteur typeCompteur;

    private int compteurInitialL;
    private int compteurInitialKm;

    private int consommationLMax;
    private int consommationKmMax;

    private int capaciteReservoir;

    @ManyToOne
    private SousFamille sousFamille;

    @ManyToOne
    private Groupe groupe;

}
