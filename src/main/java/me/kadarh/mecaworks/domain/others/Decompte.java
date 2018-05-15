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
public class Decompte extends AbstractDomain {

    Long id;
    Long cumul = 0L;
    Long objectif;
    //todo enum
    String type;
    LocalDate date;

    @ManyToOne
    Chantier chantier;
}
