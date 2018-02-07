package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"famille"})
@EqualsAndHashCode(exclude = {"famille"})
public class SousFamille {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;

    @ManyToOne
    private Famille famille;
}