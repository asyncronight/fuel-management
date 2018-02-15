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
    private int entreeF;
    private int entreeL;
    private int sortieL;
    private int sortieE;
    private int stockC;
    private int stockReel;

    @ManyToOne
    private Chantier chantier;

}
