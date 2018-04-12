package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Engin;
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
@EqualsAndHashCode(callSuper = true, exclude = {"chantierGazoil", "chantierTravail", "engin", "pompiste", "chauffeur"})
public class BonEngin extends AbstractDomain {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
    @Column(name = "DATE_BON")
    private LocalDate date;

	@Column(unique = true)
	@NotEmpty
	private String code;

	@ManyToOne
	@NotNull
	private Chantier chantierGazoil;

	@ManyToOne
	@NotNull
	private Chantier chantierTravail;

	@ManyToOne
	@NotNull
	private Engin engin;

	@ManyToOne
	@NotNull
	private Employe pompiste;

	@ManyToOne
	@NotNull
	private Employe chauffeur;

	//todo add constraints
	private Long compteurPompe;
    private Integer quantite = 0;
    private Long compteurKm;
	private Long compteurH;
	private Boolean compteurHenPanne = false;
	private Boolean compteurKmenPanne = false;
	private Boolean plein;
	private Carburant carburant;
    private Long compteurAbsoluKm = 0L;
    private Long compteurAbsoluH = 0L;
    private Float consommationKm = 0f;
    private Float consommationH = 0f;

}
