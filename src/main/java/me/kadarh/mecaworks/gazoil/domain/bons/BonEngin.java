package me.kadarh.mecaworks.gazoil.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Employe;
import me.kadarh.mecaworks.gazoil.domain.others.Engin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"chantierGazoil", "chantierTravail", "engin", "pompiste", "chauffeur"})
public class BonEngin extends AbstractDomain {

    private LocalDate date;

    @Column(unique = true)
    private String code;

    @OneToOne
    private Chantier chantierGazoil;

    @OneToOne
    private Chantier chantierTravail;

    @OneToOne
    private Engin engin;

    @ManyToOne
    private Employe pompiste;

    @ManyToOne
    private Employe chauffeur;

    private Integer quantite;
    private Long compteurKm;
    private Long compteurH;
    private Boolean enPanne;
    private Boolean plein;
    private Long compteurAbsoluKm;
    private Long compteurAbsoluH;
    private Float consommationKm;
    private Float consommationH;

}
