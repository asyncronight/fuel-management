package me.kadarh.mecaworks.repo.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.domain.others.Fournisseur;
import me.kadarh.mecaworks.repo.others.ChantierRepo;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.repo.others.FournisseurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author kadarH
 */

@Repository
@Slf4j
public class DataFakerB {

    @Autowired
    private BonEnginRepo bonEnginRepo;

    @Autowired
    private BonLivraisonRepo bonLivraisonRepo;

    @Autowired
    private BonFournisseurRepo bonFournisseurRepo;

    @Autowired
    private EnginRepo enginRepo;

    @Autowired
    private ChantierRepo chantierRepo;

    @Autowired
    private FournisseurRepo fournisseurRepo;

    public void run(String... strings) throws Exception {
        log.info("This is DataFaker of Bons");
        loadBonsEngin(80, 20);
        loadBonFournisseur(20);
    }

    private void loadBonsEngin(int n, int m) {
        for (int i = 0; i < n; i++) {
            Chantier chantierGazoil = chantierRepo.findOne(i / 20 + 1L);
            Chantier chantierTravail = chantierRepo.findOne(i / 20 + 2L);
            //Getting chantier + engin +
            Engin engin = enginRepo.getOne(i / 4 + 1L);
            //Creating the object
            BonEngin bonEngin = new BonEngin();
            bonEngin.setCode("2093" + (i * 13));
            bonEngin.setCompteur(100L + i * 5);
            bonEngin.setCompteurAbsolu(100L + i * 10);
            bonEngin.setQuantite(20 + i * 10);
            bonEngin.setChantierGazoil(chantierGazoil);
            bonEngin.setChantierTravail(chantierTravail);
            DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(LocalDate.now().format(d), d);
            bonEngin.setDate(date);
            if (i % 2 == 1) {
                bonEngin.setEnPanne(true);
                bonEngin.setPlein(true);
            } else {
                bonEngin.setEnPanne(false);
                bonEngin.setPlein(false);
            }
            bonEngin.setEngin(engin);
            bonEnginRepo.save(bonEngin);
        }
        log.info("**** " + n + " Bons Engin Loaded *****");

        for (int i = 0; i < m; i++) {
            Chantier chantierGazoil = chantierRepo.findOne(i / 10 + 1L);
            Chantier chantierTravail = chantierRepo.findOne(i / 10 + 2L);
            //Getting chantier + engin +
            Engin engin = enginRepo.getOne(i / 4 + 1L);
            BonEngin bonEngin = new BonEngin();
            bonEngin.setCode("3297" + (i * 10));
            bonEngin.setCompteur(100L + i * 5);
            bonEngin.setCompteurAbsolu(100L + i * 10);
            bonEngin.setQuantite(20 + i * 10);
            bonEngin.setChantierGazoil(chantierGazoil);
            bonEngin.setChantierTravail(chantierTravail);
            bonEngin.setDate(LocalDate.now());
            if (i % 2 == 0) {
                bonEngin.setEnPanne(true);
                bonEngin.setPlein(true);
            } else {
                bonEngin.setEnPanne(false);
                bonEngin.setPlein(false);
            }
            bonEngin.setEngin(engin);
            bonEnginRepo.save(bonEngin);
            BonLivraison bonLivraison = new BonLivraison();
            bonLivraison.setCode("KHS" + i + "LL");
            bonLivraison.setChantierDepart(chantierGazoil);
            bonLivraison.setChantierArrivee(chantierTravail);
            bonLivraison.setQuantite(20 + i * 10);
            bonLivraison.setDate(LocalDate.now());
            bonLivraison.setLivreur("Livreur" + i);
            bonLivraison.setPompiste("Pompiste" + i);
            bonLivraisonRepo.save(bonLivraison);
        }
        log.info("**** " + n + " Bons Engin + Bons Livraison direct Loaded *****");
    }


    private void loadBonFournisseur(int n) {
        for (int i = 0; i < n; i++) {
            Fournisseur fournisseur = fournisseurRepo.findOne(i / 4 + 1L);
            Chantier chantier = chantierRepo.findOne(i / 10 + 1L);

            BonFournisseur bonFournisseur = new BonFournisseur();
            bonFournisseur.setFournisseur(fournisseur);
            bonFournisseur.setChantier(chantier);
            bonFournisseur.setCode("12332" + i * 11);
            bonFournisseur.setDate(LocalDate.now());
            bonFournisseur.setQuantite(i * 10 + 1000);
            bonFournisseur.setPrixUnitaire(9f + (float) i / 123);
            bonFournisseurRepo.save(bonFournisseur);
        }
        log.info("**** " + n + " Bons Fournisseur Loaded *****");
    }
}
