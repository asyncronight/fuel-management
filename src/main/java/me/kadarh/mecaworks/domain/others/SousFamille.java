package me.kadarh.mecaworks.domain.others;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"famille", "marque"})
public class SousFamille extends AbstractDomain {

	@NotEmpty
	private String nom;
	private TypeCompteur typeCompteur;
	private Integer consommationHMax;
	private Integer consommationKmMax;
	@NotNull
	@Min(0)
	private Integer capaciteReservoir;

	@ManyToOne
    @JsonIgnore
	private Marque marque;

	@ManyToOne
    @JsonIgnore
	private Famille famille;
}