package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Classe extends AbstractDomain {

    @NotEmpty
    @Column(unique = true)
    private String nom;

}
