package me.kadarh.mecaworks.gazoil.domain.alertes;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.gazoil.config.LocalDateConverter;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;
import me.kadarh.mecaworks.gazoil.domain.bons.BonEngin;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"bon"})
@EqualsAndHashCode(callSuper = true, exclude = {"bon"})
public class AlerteEngin extends AbstractDomain {

    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;
    private TypeAlerte typeAlerte;
    private String message;
    private boolean etat;
    private float consommationKm;
    private float consommationH;

    @OneToOne
    private BonEngin bon;

}
