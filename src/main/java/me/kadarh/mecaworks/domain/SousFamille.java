package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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