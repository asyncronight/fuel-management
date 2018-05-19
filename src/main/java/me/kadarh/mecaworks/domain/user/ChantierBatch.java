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

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public Long getQuantiteLocation() {
        return quantiteLocation;
    }

    public void setQuantiteLocation(Long quantiteLocation) {
        this.quantiteLocation = quantiteLocation;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }
}
