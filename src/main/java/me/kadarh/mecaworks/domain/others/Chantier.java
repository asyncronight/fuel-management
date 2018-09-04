package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Chantier extends AbstractDomain {

    @NotEmpty
    private String nom;
	private String adresse;

	@NotNull
    private Integer stock;

    private LocalDate dateDebut;
    private LocalDate dateFin;

}
