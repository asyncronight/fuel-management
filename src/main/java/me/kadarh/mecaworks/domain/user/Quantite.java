package me.kadarh.mecaworks.domain.user;

import lombok.Data;

@Data
public class Quantite {

    private String date;
    private Long quantity = 0L;
    private Long quantiteLocation = 0L;
    private Long chargeLocataire = 0L;
    private Long chargeLocataireExterne = 0L;
    private Double consommationPrevue = 0.2D;
	//todo : fill with real value
	private Float prix = 10F;

    public Quantite(String date, Long quantity, Long quantiteLocation, Long chargeLocataire, Long chargeLocataireExterne, Double consommationPrevue) {
        this.date = date;
        this.quantity = quantity;
        this.quantiteLocation = quantiteLocation;
        this.chargeLocataire = chargeLocataire;
        this.chargeLocataireExterne = chargeLocataireExterne;
        this.consommationPrevue = consommationPrevue;
    }

    public Quantite() {
    }
}
