package me.kadarh.mecaworks.domain.user;

import lombok.Data;

@Data
public class Quantite {

    private Long quantity = 0L;
    private Long quantiteLocation = 0L;
    private Long chargeLocataire = 0L;
    private Long chargeLocataireExterne = 0L;
    private Double consommationPrevue = 0.2D;

    public Quantite(Long quantite, Long quantiteLocation, Long chargeLocataire, Long chargeLocataireExterne, Double consommationPrevue) {
        this.quantity = quantite;
        this.quantiteLocation = quantiteLocation;
        this.chargeLocataire = chargeLocataire;
        this.chargeLocataireExterne = chargeLocataireExterne;
        this.consommationPrevue = consommationPrevue;
    }

    public Quantite() {
    }
}
