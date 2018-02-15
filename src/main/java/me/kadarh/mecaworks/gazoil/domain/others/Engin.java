package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"sousFamille", "groupe"})
public class Engin extends AbstractDomain {

    @Column(unique = true)
    private String code;

    private String numeroSerie;
    private int compteurInitialL;
    private int compteurInitialKm;

    @ManyToOne
    private SousFamille sousFamille;

    @ManyToOne
    private Groupe groupe;

}
