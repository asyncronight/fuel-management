package me.kadarh.mecaworks.gazoil.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Fournisseur;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"fournisseur", "chantier"})
public class BonFournisseur extends AbstractDomain {

    private LocalDate date;

    @Column(unique = true)
    private String code;

    @OneToOne
    private Fournisseur fournisseur;

    @OneToOne
    private Chantier chantier;

    private Integer quantite;
    private Integer prixUnitaire;

}
