package me.kadarh.mecaworks.domain.user;

import lombok.Data;

@Data
public class Quantite {

    private String date;
    private Long quantity = 0L;
    private Long quantiteLocation = 0L;
    private Long chargeLocataire = 0L;
    private Long chargeLocataireExterne = 0L;
    private Float prix = 8.5f;
    private Long consommationPrevue = 0L;
	private Long gazoilAchete = 0L;
	private Long gazoilFlotant = 0L;

    public Quantite(String date, Long quantity, Long quantiteLocation, Long chargeLocataire, Long chargeLocataireExterne, Float prix, Long consommationPrevue, Long gazoilAchete, Long gazoilFlotant) {
        this.date = date;
        this.quantity = quantity;
        this.quantiteLocation = quantiteLocation;
        this.chargeLocataire = chargeLocataire;
        this.chargeLocataireExterne = chargeLocataireExterne;
        this.prix = prix;
        this.consommationPrevue = consommationPrevue;
        this.gazoilAchete = gazoilAchete;
        this.gazoilFlotant = gazoilFlotant;
    }

    public Quantite() {
    }
}
