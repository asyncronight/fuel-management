package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Employe;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"chantierDepart", "chantierArrivee", "transporteur", "pompiste"})
public class BonLivraison extends AbstractDomain {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate date;

	@Column(unique = true)
	@NotEmpty
	private String code;

	@NotNull
	private Integer quantite;

    @ManyToOne
	@NotNull
	private Employe transporteur;

    @ManyToOne
	@NotNull
	private Employe pompiste;

	@ManyToOne
	@NotNull
	private Chantier chantierDepart;

	@ManyToOne
	@NotNull
	private Chantier chantierArrivee;


}
