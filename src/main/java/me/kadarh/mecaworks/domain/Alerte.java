package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.domain.Bons.BonEngin;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"bonEngin", "chantierDepart"})
@EqualsAndHashCode(callSuper = true, exclude = {"bonEngin", "chantierDepart"})
public class Alerte extends AbstractDomain {

    @ManyToOne
    private TypeAlerte typeAlerte;

    @OneToOne
    private BonEngin bonEngin;

    @OneToOne
    private Chantier chantierDepart;

    @OneToOne
    private Chantier chantierArrivee;

    private boolean etat;
    private LocalDate date;
    private float consommation;

}
