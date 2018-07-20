package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Fournisseur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"fournisseur", "chantier"})
public class BonFournisseur extends AbstractDomain {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate date;

	@NotEmpty
	@Column(unique = true)
	private String code;

	@Positive
	@NotNull
	private Integer quantite;

	@NotNull
	@Positive
	private Float prixUnitaire;

	@ManyToOne
	@NotNull
	private Fournisseur fournisseur;

	@NotNull
	@ManyToOne
	private Chantier chantier;

}
