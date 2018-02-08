package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"famille"})
@EqualsAndHashCode(callSuper = true, exclude = {"famille"})
public class SousFamille extends AbstractDomain {

    private String nom;

    @ManyToOne
    private Famille famille;
}