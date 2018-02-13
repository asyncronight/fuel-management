package me.kadarh.mecaworks.gazoil.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.gazoil.config.LocalDateConverter;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Employe;
import me.kadarh.mecaworks.gazoil.domain.others.Engin;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"chantierGazoil", "chantierTravail", "engin"})
@EqualsAndHashCode(callSuper = true, exclude = {"chantierGazoil", "chantierTravail", "engin"})
public class BonEngin extends AbstractDomain {

    @Convert(converter = LocalDateConverter.class)
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

    private int quantite;
    private long compteurKm;
    private long compteurH;
    private boolean enPanne;
    private boolean plein;
    private long compteurAbsoluKm;
    private long compteurAbsoluH;
    private float consommationKm;
    private float consommationH;

}
