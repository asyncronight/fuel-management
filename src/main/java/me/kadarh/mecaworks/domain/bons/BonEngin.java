package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.config.LocalDateConverter;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Engin;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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

    private int quantite;
    private long compteur;
    private boolean enPanne;
    private boolean plein;
    private long compteurAbsolu;
    private float consommation;

}
