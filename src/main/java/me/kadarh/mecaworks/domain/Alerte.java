package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"bon","chantier"})
@EqualsAndHashCode(exclude = {"bon","chantier"})
public class Alerte {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TypeAlerte typeAlerte;

    @OneToOne
    private Bon bon;

    @OneToOne
    private Chantier chantier;

    private boolean etat;
    private LocalDate date;
    private float consommation;


}
