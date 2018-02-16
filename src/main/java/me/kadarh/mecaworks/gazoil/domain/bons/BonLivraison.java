package me.kadarh.mecaworks.gazoil.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Employe;

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
@EqualsAndHashCode(callSuper = true, exclude = {"chantierDepart", "chantierArrivee", "transporteur", "pompiste"})
public class BonLivraison extends AbstractDomain {

    private LocalDate date;

    @Column(unique = true)
    private String code;

    @OneToOne
    private Chantier chantierDepart;

    @OneToOne
    private Chantier chantierArrivee;

    private Integer quantite;
    @ManyToOne
    private Employe transporteur;
    @ManyToOne
    private Employe pompiste;

}
