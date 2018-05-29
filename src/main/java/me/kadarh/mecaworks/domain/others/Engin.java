package me.kadarh.mecaworks.domain.others;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"sousFamille", "groupe"})
public class Engin extends AbstractDomain {

	@Column(unique = true)
	@NotEmpty
	private String code;
	@NotEmpty
	private String numeroSerie;
	private Integer compteurInitialH;
	private Integer compteurInitialKm;
    private Integer objectif = 12;
    private Integer prixLocationJournalier;

	@ManyToOne
	private SousFamille sousFamille;

	@ManyToOne
    @JsonIgnore
	private Groupe groupe;

    private Float consommationMoyenne = 30f;

}
