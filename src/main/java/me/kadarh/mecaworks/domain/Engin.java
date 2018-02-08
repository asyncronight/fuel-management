package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    private int compteurInitial;

    private int consommationMax;

    private int capaciteReservoir;

    @ManyToOne
    private SousFamille sousFamille;

    @ManyToOne
    private Groupe groupe;

}
