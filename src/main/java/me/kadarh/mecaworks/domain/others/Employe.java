package me.kadarh.mecaworks.domain.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;

/**
 * @author kadarH
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Employe extends AbstractDomain {


	private String nom;
	private String metier;

	public Employe(String nom) {
		this.nom = nom;
	}

	public Employe() {
	}
}
