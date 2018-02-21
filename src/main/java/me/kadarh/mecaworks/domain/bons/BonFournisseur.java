package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Fournisseur;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"fournisseur", "chantier"})
public class BonFournisseur extends AbstractDomain {

	private LocalDate date;

	@Column(unique = true)
	private String code;

	@ManyToOne
	private Fournisseur fournisseur;

	@ManyToOne
	private Chantier chantier;

	private Integer quantite;
	private Float prixUnitaire;

}
