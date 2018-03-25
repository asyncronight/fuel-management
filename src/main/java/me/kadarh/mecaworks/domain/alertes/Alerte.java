package me.kadarh.mecaworks.domain.alertes;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Alerte extends AbstractDomain {

	private LocalDate date;
	private TypeAlerte typeAlerte;
	private String message;
	private Boolean etat;

	private Long idBon;

}
