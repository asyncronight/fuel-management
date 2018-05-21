package me.kadarh.mecaworks.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.kadarh.mecaworks.domain.AbstractDomain;
import me.kadarh.mecaworks.domain.others.Chantier;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "chantier")
@ToString(exclude = "chantier")
public class ChantierBatch extends AbstractDomain {

    private int mois;
    private int annee;
    private Long quantite = 0L;
    private Long quantiteLocation = 0L;
    @ManyToOne
    private Chantier chantier;

    public ChantierBatch() {
    }

    public ChantierBatch(int mois, int annee, Long quantite, Long quantiteLocation, Chantier chantier) {
        this.mois = mois;
        this.annee = annee;
        this.quantite = quantite;
        this.quantiteLocation = quantiteLocation;
        this.chantier = chantier;
    }
}
