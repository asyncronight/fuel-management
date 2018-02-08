package me.kadarh.mecaworks.config;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.Bons.BonEngin;
import me.kadarh.mecaworks.domain.*;
import me.kadarh.mecaworks.repo.*;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author kadarH
 */

@Repository
@Slf4j
public class DataFaker implements CommandLineRunner {

    @Autowired
    private ChantierRepo chantierRepo;
    @Autowired
    private GroupeRepo groupeRepo;
    @Autowired
    private FamilleRepo familleRepo;
    @Autowired
    private SousFamilleRepo sousFamilleRepo;
    @Autowired
    private BonEnginRepo bonEnginRepo;
    @Autowired
    private EnginRepo enginRepo;


    @Override
    public void run(String... strings) throws Exception {
        loadGroupe(5);
        loadChantiers(5);
        loadFamille(5);
        loadSousFamilles(10);
        loadEngins(20);
        loadBons(80);
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

    // Loading Familles
    private void loadFamille(int n){
        for (int i=0;i<n;i++){
            Famille famille = new Famille();
            famille.setNom("famille"+(i+1));
            familleRepo.save(famille);
        }
        log.info("**** "+n+" Famille Loaded *****");
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
            //Setting attributes
            Engin engin = new Engin();
            engin.setCode("Pelle"+(i+1));
            engin.setConsommationMax(200+i*10);
            engin.setCapaciteReservoir(100+i*10);
            engin.setCompteurInitial(1000+(i*10));
            engin.setNumeroSerie("TPF"+i+"zz"+i);
            engin.setGroupe(groupe);
            engin.setSousFamille(sousFamille);
            //Persisting
            enginRepo.save(engin);
        }
        log.info("**** "+n+" Engin Loaded *****");
    }

    private void loadBons(int n){
        for (int i=0;i<n;i++) {
            BonEngin bonEngin = new BonEngin();
            bonEngin.setCode("3297" + (i * 10));
            bonEngin.setCompteur(100L + i * 5);
            bonEngin.setCompteurAbsolu(100L + i * 10);
            bonEngin.setQuantite(20 + i * 10);
            DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(LocalDate.now().format(d),d);
            bonEngin.setDate(date);
            if (i % 2 == 1) bonEngin.setEnPanne(true);
            else bonEngin.setEnPanne(false);
            if (i % 2 == 1) bonEngin.setPlein(true);
            else bonEngin.setPlein(false);
            //Getting chantier + engin +
            Engin engin = enginRepo.getOne(i/4 +1L);
            bonEngin.setEngin(engin);
            bonEnginRepo.save(bonEngin);
        }
        log.info("**** " + n + " BonEngin Loaded *****");
    }
}
