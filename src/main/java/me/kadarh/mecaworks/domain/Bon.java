package me.kadarh.mecaworks.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.config.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author kadarH
 */

@Entity
@Data
@ToString(exclude = {"chantier","engin","groupe"})
@EqualsAndHashCode(exclude = {"chantier","engin","groupe"})
public class Bon {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String code;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    @OneToOne
    private Engin engin;

    @OneToOne
    private Chantier chantier;
    private int quantite;
    private long compteur;
    private long compteurAbsolu;
    private float consommation;
    private boolean enPanne;
    private boolean plein;

    @ManyToOne
    private Groupe groupe;

}
