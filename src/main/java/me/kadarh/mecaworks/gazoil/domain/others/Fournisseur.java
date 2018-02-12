package me.kadarh.mecaworks.gazoil.domain.others;

import lombok.Data;
import me.kadarh.mecaworks.gazoil.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */

@Entity
@Data
public class Fournisseur extends AbstractDomain {

    private String nom;

}
