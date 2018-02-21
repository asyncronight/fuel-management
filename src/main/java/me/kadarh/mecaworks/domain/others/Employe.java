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
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Employe extends AbstractDomain {


	@NotEmpty
	@Column(unique = true)
	private String nom;

	@NotEmpty
	private String metier;

	public Employe(String nom) {
		this.nom = nom;
	}

	public Employe() {
	}
}
