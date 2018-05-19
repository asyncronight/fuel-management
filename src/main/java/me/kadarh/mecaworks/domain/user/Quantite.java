package me.kadarh.mecaworks.domain.user;

import lombok.Data;
import me.kadarh.mecaworks.domain.AbstractDomain;

@Data
public class Quantite extends AbstractDomain {

    private Long quantite = 0L;
    private Long quantiteLocation = 0L;

    public Quantite(Long quantite, Long quantiteLocation) {
        this.quantite = quantite;
        this.quantiteLocation = quantiteLocation;
    }

    public Quantite() {
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
}
