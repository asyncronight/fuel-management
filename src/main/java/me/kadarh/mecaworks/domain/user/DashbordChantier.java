package me.kadarh.mecaworks.domain.user;

import lombok.Data;
import me.kadarh.mecaworks.domain.others.Chantier;

import java.util.List;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 30/05/18
 */
@Data
public class DashbordChantier {

    private List<Quantite> quantitesDays;
    private List<Quantite> quantitesMonths;
    private Chantier chantier;

    public DashbordChantier(List<Quantite> quantitesDays, List<Quantite> quantitesMonths, Chantier chantier) {
        this.quantitesDays = quantitesDays;
        this.quantitesMonths = quantitesMonths;
        this.chantier = chantier;
    }
}
