package me.kadarh.mecaworks.gazoil.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.gazoil.config.LocalDateConverter;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;
import me.kadarh.mecaworks.gazoil.domain.others.Chantier;
import me.kadarh.mecaworks.gazoil.domain.others.Employe;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"chantierDepart", "chantierArrivee"})
@EqualsAndHashCode(callSuper = true, exclude = {"chantierDepart", "chantierArrivee"})
public class BonLivraison extends AbstractDomain {

    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    @Column(unique = true)
    private String code;

    @OneToOne
    private Chantier chantierDepart;

    @OneToOne
    private Chantier chantierArrivee;

    private int quantite;
    @ManyToOne
    private Employe transporteur;
    @ManyToOne
    private Employe pompiste;

}
