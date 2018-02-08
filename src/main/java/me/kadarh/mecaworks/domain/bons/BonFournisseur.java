package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.config.LocalDateConverter;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Fournisseur;

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
@ToString(exclude = {"fournisseur", "chantier"})
@EqualsAndHashCode(callSuper = true, exclude = {"fournisseur", "chantier"})
public class BonFournisseur extends AbstractDomain {

    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    @Column(unique = true)
    private String code;

    @OneToOne
    private Fournisseur fournisseur;

    @OneToOne
    private Chantier chantier;

    private int quantite;
    private float prixUnitaire;
    private float consommation;

}
