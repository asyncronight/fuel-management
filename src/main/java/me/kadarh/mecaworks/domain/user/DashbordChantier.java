package me.kadarh.mecaworks.domain.user;

import lombok.Data;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Stock;

import java.time.LocalDate;
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
    private List<Stock> stocks;
    private Long stockC;
    private Long ecartPlus;
    private Long ecartMoins;
    private LocalDate dateMAJ;
    private Chantier chantier;

    public DashbordChantier(List<Quantite> quantitesDays, List<Quantite> quantitesMonths, List<Stock> stocks, Long stockC, Long ecartPlus, Long ecartMoins, LocalDate dateMAJ, Chantier chantier) {
        this.quantitesDays = quantitesDays;
        this.quantitesMonths = quantitesMonths;
        this.stocks = stocks;
        this.stockC = stockC;
        this.ecartPlus = ecartPlus;
        this.ecartMoins = ecartMoins;
        this.dateMAJ = dateMAJ;
        this.chantier = chantier;
    }
}
