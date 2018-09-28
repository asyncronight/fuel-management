package me.kadarh.mecaworks.domain.others;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.kadarh.mecaworks.domain.AbstractDomain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"chantier"})
public class Stock extends AbstractDomain {

	private LocalDate date;
    private Integer entreeF = 0;
    private Integer entreeL = 0;
    private Integer sortieL = 0;
    private Integer sortieE = 0;
    private Integer stockC = 0;
    private Integer stockReel = 0;
    private Integer ecart_plus = 0;
    private Integer ecart_moins = 0;

	@ManyToOne
    @JsonIgnore
	private Chantier chantier;

    private Long id_Bon;
    private TypeBon typeBon;
    private Integer quantite;

}
