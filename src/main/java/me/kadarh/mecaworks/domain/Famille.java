package me.kadarh.mecaworks.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author kadarH
 */

@Entity
@Data
public class Famille {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;

}
