package me.kadarh.mecaworks.domain.bons;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.service.impl.bons.BonFilterServiceImpl;
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

	private Long compteurPompe = 0L;

    private Integer quantite = 0;

    private Long compteurKm = 0L;

    private Long compteurH = 0L;
	private Boolean compteurHenPanne = false;
	private Boolean compteurKmenPanne = false;
	private Boolean plein;
	@NotNull
	private Carburant carburant = Carburant.Gazoil;
    private Long compteurAbsoluKm = 0L;
    private Long compteurAbsoluH = 0L;
    private Float consommationKm = 0f;
    private Float consommationH = 0f;
    private Long nbrHeures = 0L;
    private Long nbrKm = 0L;
    private Long consommationPrevu = 0L;
    private Long chargeHoraire = 0L;

	public BonEngin() {
	}
	/**
	 * This constructer is only used in {@link BonFilterServiceImpl} when
	 * creating a new BonEngin from an existing object (so we don't
	 * accidentally change the existing values and then get saved
	 * automatically) and preserve some of the attributes for later use.
	 *
	 * Don't use it in any other case !!!
	 *
	 * @param bonEngin
	 */
	public BonEngin(BonEngin bonEngin) {
		this.date = bonEngin.getDate();
		this.chantierTravail = bonEngin.getChantierTravail();
		this.engin = bonEngin.getEngin();
		this.consommationH = bonEngin.getConsommationH();
		this.consommationKm = bonEngin.getConsommationKm();
		this.chauffeur = bonEngin.getChauffeur();
		this.compteurH = bonEngin.getCompteurH();
		this.compteurKm = bonEngin.getCompteurKm();
		this.compteurAbsoluH = bonEngin.getCompteurAbsoluH();
		this.compteurAbsoluKm = bonEngin.getCompteurAbsoluKm();
	}
}
