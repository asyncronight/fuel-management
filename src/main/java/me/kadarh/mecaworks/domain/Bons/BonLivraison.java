package me.kadarh.mecaworks.domain.Bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.config.LocalDateConverter;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.Chantier;

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
    private float consommation;

}
