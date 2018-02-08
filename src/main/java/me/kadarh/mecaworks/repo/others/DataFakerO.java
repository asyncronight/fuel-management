package me.kadarh.mecaworks.repo.others;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.repo.alertes.DataFakerA;
import me.kadarh.mecaworks.repo.bons.DataFakerB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

/**
 * @author kadarH
 */

@Repository
@Slf4j
public class DataFakerO implements CommandLineRunner {


    @Autowired
    private ChantierRepo chantierRepo;
    @Autowired
    private GroupeRepo groupeRepo;
    @Autowired
    private FamilleRepo familleRepo;
    @Autowired
    private SousFamilleRepo sousFamilleRepo;
    @Autowired
    private EnginRepo enginRepo;
    @Autowired
    private FournisseurRepo fournisseurRepo;

    @Autowired
    private DataFakerA dataFakerA;

    @Autowired
    private DataFakerB dataFakerB;


    @Override
    public void run(String... strings) throws Exception {
        log.info("This is the DataFaker Of Other Domains");
        loadGroupe(5);
        loadChantiers(5);
        loadFamille(5);
        loadSousFamilles(10);
        loadEngins(20);
        loadFournisseur(20);
        dataFakerA.run();
        dataFakerB.run();
    }

    // Loading Groupes
    private void loadGroupe(int n){
        for (int i=0;i<n;i++){
            Groupe groupe = new Groupe();
            groupe.setNom("groupe"+(i+1));
            groupeRepo.save(groupe);
        }
        log.info("**** "+n+" Groupe Loaded *****");
    }

    // Loading Chantiers
    private void loadChantiers(int n){
        for (int i=0;i<n;i++){
            Chantier chantier = new Chantier();
            chantier.setNom("chantier"+(i+1));
            chantier.setAdresse("Kenitra");
            chantier.setStock((i%2)*10);
            chantierRepo.save(chantier);
        }
        log.info("**** "+n+" Chantier Loaded *****");
    }

    // Loading Fournisseur
    private void loadFournisseur(int n) {
        for (int i=0;i<n;i++){
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setNom("fournisseur" + (i + 1));
            fournisseurRepo.save(fournisseur);
        }
        log.info("**** " + n + " Fournisseur Loaded *****");
    }

    // Loading Familles
    private void loadFamille(int n) {
        for (int i = 0; i < n; i++) {
            Famille famille = new Famille();
            famille.setNom("famille" + (i + 1));
            familleRepo.save(famille);
        }
        log.info("**** " + n + " Famille Loaded *****");
    }

    // Loading SousFamilles
    private void loadSousFamilles(int n){
        for (int i=0;i<n;i++){
            Famille famille = familleRepo.getOne((i/2)+1L);
            SousFamille sousFamille = new SousFamille();
            sousFamille.setNom("sousFamille"+(i+1));
            sousFamille.setFamille(famille);
            sousFamilleRepo.save(sousFamille);
        }
        log.info("**** "+n+" SousFamille Loaded *****");
    }

    // Loading Engins
    private void loadEngins(int n){
        for (int i=0;i<n;i++){
            //Getting groupe and sous-famille
            Groupe groupe = groupeRepo.findOne(i/4 +1L);
            SousFamille sousFamille = sousFamilleRepo.findOne(i/2 +1L);
            //Creation the object
            Engin engin = new Engin();
            engin.setCode("Pelle"+(i+1));
            engin.setCapaciteReservoir(10 + i * 10);
            engin.setNumeroSerie("TPF"+i+"zz"+i);
            engin.setGroupe(groupe);
            engin.setSousFamille(sousFamille);
            if (i % 3 == 0) {
                engin.setTypeCompteur(TypeCompteur.km_l);
                engin.setCompteurInitialL(1000 + (i * 10));
                engin.setCompteurInitialKm(1000 + (i * 10));
                engin.setConsommationKmMax(20 + i * 10);
                engin.setConsommationLMax(20 + i * 10);
            } else if (i % 3 == 1) {
                engin.setTypeCompteur(TypeCompteur.l);
                engin.setConsommationLMax(20 + i * 10);
                engin.setCompteurInitialL(1000 + (i * 10));
            } else if (i % 3 == 2) {
                engin.setTypeCompteur(TypeCompteur.km);
                engin.setConsommationKmMax(20 + i * 10);
                engin.setCompteurInitialKm(1000 + (i * 10));
            }
            //Persisting
            enginRepo.save(engin);
        }
        log.info("**** "+n+" Engin Loaded *****");
    }


}
