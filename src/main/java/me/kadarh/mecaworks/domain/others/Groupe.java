package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */

@Entity
@Data
public class Groupe extends AbstractDomain {

    private String nom;
}
