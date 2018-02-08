package me.kadarh.mecaworks.domain;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author kadarH
 */

@Entity
@Data
public class Chantier extends AbstractDomain {

    private String nom;
    private String adresse;
    private int stock;

}
