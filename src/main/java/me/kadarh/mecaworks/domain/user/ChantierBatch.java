package me.kadarh.mecaworks.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long chargeLocataire = 0L;
    private Long chargeLocataireExterne = 0L;
    private Long consommationPrevue = 0L;

    @ManyToOne
    private Chantier chantier;

    public ChantierBatch() {
    }

    public ChantierBatch(int mois, int annee, Long quantite, Long quantiteLocation, Long chargeLocataire, Long chargeLocataireExterne, Long consommationPrevue, Chantier chantier) {
        this.mois = mois;
        this.annee = annee;
        this.quantite = quantite;
        this.quantiteLocation = quantiteLocation;
        this.chargeLocataire = chargeLocataire;
        this.chargeLocataireExterne = chargeLocataireExterne;
        this.consommationPrevue = consommationPrevue;
        this.chantier = chantier;
    }

    @Override
    @JsonIgnore
    public Long getId() {
        return this.id;
    }
}
