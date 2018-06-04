package me.kadarh.mecaworks.domain.user;
/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 30/05/18
 */
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
    private Float prix = 10F;
    private Long gazoilAchete = 0L;
    private Long gazoilFlotant = 0L;

    @ManyToOne
    private Chantier chantier;

    public ChantierBatch() {
    }

    public ChantierBatch(int mois, int annee, Long quantite, Long quantiteLocation, Long chargeLocataire, Long chargeLocataireExterne, Long consommationPrevue, Float prix, Long gazoilAchete, Long gazoilFlotant, Chantier chantier) {
        this.mois = mois;
        this.annee = annee;
        this.quantite = quantite;
        this.quantiteLocation = quantiteLocation;
        this.chargeLocataire = chargeLocataire;
        this.chargeLocataireExterne = chargeLocataireExterne;
        this.consommationPrevue = consommationPrevue;
        this.prix = prix;
        this.gazoilAchete = gazoilAchete;
        this.gazoilFlotant = gazoilFlotant;
        this.chantier = chantier;
    }

    @Override
    @JsonIgnore
    public Long getId() {
        return this.id;
    }
}
