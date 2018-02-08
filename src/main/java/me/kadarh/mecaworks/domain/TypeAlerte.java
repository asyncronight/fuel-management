package me.kadarh.mecaworks.domain;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author kadarH
 */
@Entity
@Data
public class TypeAlerte extends AbstractDomain {

    private String nom;

}
