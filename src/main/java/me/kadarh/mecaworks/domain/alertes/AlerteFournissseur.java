package me.kadarh.mecaworks.domain.alertes;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.others.TypeAlerte;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"bon"})
@EqualsAndHashCode(callSuper = true, exclude = {"bon"})
public class AlerteFournissseur extends AbstractDomain {

    @ManyToOne
    private TypeAlerte typeAlerte;

    @OneToOne
    private BonFournisseur bon;

    private boolean etat;
    private LocalDate date;
    private float consommation;

}
