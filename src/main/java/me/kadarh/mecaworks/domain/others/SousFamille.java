package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"famille"})
public class SousFamille extends AbstractDomain {

	private String nom;
	private TypeCompteur typeCompteur;
	private Integer consommationLMax;
	private Integer consommationKmMax;
	private Integer capaciteReservoir;

	@ManyToOne
	private Famille famille;
}