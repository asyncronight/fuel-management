package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"sousFamille","groupe"})
@EqualsAndHashCode(exclude = {"sousFamille","groupe"})
public class Engin {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String code;
    private String numeroSerie;
    private int compteurInitial;
    private int consommationMax;
    private boolean typeCompteur;
    private int capaciteReservoir;

    @ManyToOne
    private SousFamille sousFamille;

    @ManyToOne
    private Groupe groupe;

}
