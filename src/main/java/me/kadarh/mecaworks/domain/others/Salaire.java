package me.kadarh.mecaworks.domain.others;

import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 08/05/18
 */
public class Salaire extends AbstractDomain {

    private Long id;
    private Long montant = 0L;
    private LocalDate date;

    @ManyToOne
    private Chantier chantier;
}
