package me.kadarh.mecaworks.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dashbord {

	private List<ChantierBatch> chantierBatch = new ArrayList<>();
	private List<Quantite> quantites = new ArrayList<>();

	@JsonIgnore
	public Long getTotaleGazoile() {
		return quantites.stream()
				.mapToLong(Quantite::getQuantity)
				.sum() - quantites.get(0).getQuantity();
	}

	@JsonIgnore
	public Double getTotaleGazoileDh() {
		return quantites.stream()
				.mapToDouble(quantite -> quantite.getQuantity() * quantite.getPrix())
				.sum() - quantites.get(0).getQuantity() * quantites.get(0).getPrix();
	}

	@JsonIgnore
	public Long getTotaleGazoileLocation() {
		return quantites.stream()
				.mapToLong(Quantite::getQuantiteLocation)
				.sum() - quantites.get(0).getQuantiteLocation();
	}

	@JsonIgnore
	public Double getTotaleGazoileLocationDh() {
		return quantites.stream()
				.mapToDouble(quantite -> quantite.getQuantiteLocation() * quantite.getPrix())
				.sum() - quantites.get(0).getQuantiteLocation() * quantites.get(0).getPrix();
	}

	@JsonIgnore
	public Long getTotaleGazoileInterne() {
		return quantites.stream()
				.mapToLong(q -> q.getQuantity() - q.getQuantiteLocation())
				.sum() - (quantites.get(0).getQuantity() - quantites.get(0).getQuantiteLocation());
	}

	@JsonIgnore
	public Double getTotaleGazoileInterneDh() {
		return quantites.stream()
				.mapToDouble(q -> (q.getQuantity() - q.getQuantiteLocation()) * q.getPrix())
				.sum() - (quantites.get(0).getQuantity() - quantites.get(0).getQuantiteLocation()) * quantites.get(0).getPrix();
	}

	@JsonIgnore
	public Long getConsommationTotalePrevue() {
		return quantites.stream()
				.mapToLong(Quantite::getConsommationPrevue)
				.sum() - quantites.get(0).getConsommationPrevue();
	}

	@JsonIgnore
	public Double getConsommationTotalePrevueDh() {
		return quantites.stream()
				.mapToDouble(q -> q.getConsommationPrevue() * q.getPrix())
				.sum() - quantites.get(0).getConsommationPrevue() * quantites.get(0).getPrix();
	}

	@JsonIgnore
	public Long getChargeLocataireInterne() {
		return quantites.stream()
				.mapToLong(Quantite::getChargeLocataire)
				.sum() - quantites.get(0).getChargeLocataire();
	}

	@JsonIgnore
	public Long getChargeLocataireExterne() {
		return quantites.stream()
				.mapToLong(Quantite::getChargeLocataireExterne)
				.sum() - quantites.get(0).getChargeLocataireExterne();
	}

	@JsonIgnore
	public Long getChargeLocataireTotale() {
		return quantites.stream()
				.mapToLong(quantite -> quantite.getChargeLocataire() + quantite.getChargeLocataireExterne())
				.sum() - (quantites.get(0).getChargeLocataire() - quantites.get(0).getChargeLocataireExterne());
	}
}
