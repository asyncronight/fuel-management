package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"chantier"})
public class Stock extends AbstractDomain {

    private LocalDate date;
    private Integer entreeF;
    private Integer entreeL;
    private Integer sortieL;
    private Integer sortieE;
    private Integer stockC;
    private Integer stockReel;

    @ManyToOne
    private Chantier chantier;

}
