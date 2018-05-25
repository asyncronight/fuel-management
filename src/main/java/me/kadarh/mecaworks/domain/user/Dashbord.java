package me.kadarh.mecaworks.domain.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dashbord {

	private List<ChantierBatch> chantierBatch = new ArrayList<>();
	private List<Quantite> quantites = new ArrayList<>();

	//todo exclude last month (because there is 13 month in quantites) or just fill 12 month
	// todo add prix to Quantite class
	public Long getTotaleGazoile() {
		return quantites.stream().mapToLong(Quantite::getQuantity).sum();
	}

	public Long getTotaleGazoileDh() {
		return quantites.stream().mapToLong(quantite -> quantite.getQuantity() * 10).sum();
	}

	public Long getTotaleGazoileLocation() {
		return quantites.stream().mapToLong(Quantite::getQuantiteLocation).sum();
	}

	public Long getTotaleGazoileLocationDh() {
		return quantites.stream().mapToLong(quantite -> quantite.getQuantiteLocation() * 10).sum();
	}

	public Long getTotaleGazoileInterne() {
		return quantites.stream().mapToLong(q -> q.getQuantity() - q.getQuantiteLocation()).sum();
	}

	public Long getTotaleGazoileInterneDh() {
		return quantites.stream().mapToLong(q -> (q.getQuantity() - q.getQuantiteLocation()) * 10).sum();
	}

	public Long getChargeLocataireInterne() {
		return quantites.stream().mapToLong(Quantite::getChargeLocataire).sum();
	}

	public Long getChargeLocataireExterne() {
		return quantites.stream().mapToLong(Quantite::getChargeLocataireExterne).sum();
	}

	public Long getChargeLocataireTotale() {
		return quantites.stream().mapToLong(quantite -> quantite.getChargeLocataire() + quantite.getChargeLocataireExterne()).sum();
	}
}
