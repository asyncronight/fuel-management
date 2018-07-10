package me.kadarh.mecaworks.repo.others;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.bons.BonFournisseurRepo;
import me.kadarh.mecaworks.repo.bons.BonLivraisonRepo;
import me.kadarh.mecaworks.repo.user.BatchFaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

/**
 * @author kadarH
 */

@Component
@Transactional
@Slf4j
//@Profile("dev")
public class DataFakerO /*implements CommandLineRunner */ {

	@Autowired
	private ChantierRepo chantierRepo;
	@Autowired
	private EmployeRepo employeRepo;
	@Autowired
	private GroupeRepo groupeRepo;
	@Autowired
	private ClasseRepo classeRepo;
	@Autowired
	private FamilleRepo familleRepo;
	@Autowired
	private SousFamilleRepo sousFamilleRepo;
	@Autowired
    private MarqueRepo marqueRepo;
    @Autowired
    private EnginRepo enginRepo;
	@Autowired
	private FournisseurRepo fournisseurRepo;
	@Autowired
	BonEnginRepo bonEnginRepo;
	@Autowired
	BonLivraisonRepo bonLivraisonRepo;
	@Autowired
	BonFournisseurRepo bonFournisseurRepo;
    @Autowired
    BatchFaker batchFaker;

    @Scheduled(fixedDelay = 20000)
    public void run() /*throws Exception*/ {
        log.info("This is the DataFaker Of Other Domains");
		loadGroupe(5);
        loadChantiers(10);
        loadClasses(2);
        loadFamille(5);
		loadMarques(5);
		loadSousFamilles(10);
        loadEngins(40);
        loadFournisseur(10);
        loadEmploye(70);
        loadBonEngin(1000);
        loadBonLivraison(300);
        loadBonFournisseur(100);
        batchFaker.insertBatchChantier();
    }

	private void loadGroupe(int n) {
		for (int i = 0; i < n; i++) {
			Groupe groupe = new Groupe();
			groupe.setNom("groupe" + (i + 1));
			if (i % 3 == 0) groupe.setLocataire(true);
			else groupe.setLocataire(false);
			groupeRepo.save(groupe);
		}
		log.info(n + " Groupe Loaded *****");
	}
	private void loadChantiers(int n) {
		for (int i = 0; i < n; i++) {
			Chantier chantier = new Chantier();
			chantier.setNom("chantier" + (i + 1));
			chantier.setAdresse("Kenitra" + i);
            chantier.setStock(988 + i * 3);
            chantierRepo.save(chantier);
        }
		log.info(n + " Chantier Loaded *****");
	}

    private void loadClasses(int n) {
        for (int i = 0; i < n; i++) {
            Classe classe = new Classe();
            classe.setNom("Classe" + (i + 1));
            classeRepo.save(classe);
        }
    }
	private void loadFamille(int n) {
		for (int i = 0; i < n; i++) {
			Famille famille = new Famille();
			famille.setNom("famille" + (i + 1));
			if (i <= 2)
                famille.setClasse(classeRepo.findById((i / 200) + 1L).get());
            else
                famille.setClasse(classeRepo.findById((i / 200) + 1L).get());
            familleRepo.save(famille);
        }
		log.info(n + " Famille Loaded *****");
	}

    private void loadMarques(int n) {
        for (int i = 0; i < n; i++) {
            Marque marque = new Marque();
            marque.setNom("marque" + (i + 1));
            marqueRepo.save(marque);
        }
        log.info(n + " Marque Loaded *****");
    }

    private void loadSousFamilles(int n) {
        for (int i = 0; i < n; i++) {
			Famille famille = familleRepo.getOne((i / 2) + 1L);
            Marque marque = marqueRepo.getOne((i / 2) + 1L);
            SousFamille sousFamille = new SousFamille();
            sousFamille.setNom("sousFamille" + (i + 1));
            sousFamille.setFamille(famille);
            sousFamille.setMarque(marque);
            sousFamille.setCapaciteReservoir(100 + i % 2 * 10);
            if (i % 3 == 0) {
                sousFamille.setTypeCompteur(TypeCompteur.KM_H);
                sousFamille.setConsommationKmMax(10 + i * 2);
                sousFamille.setConsommationHMax(20 + i * 2);
            } else if (i % 3 == 1) {
                sousFamille.setTypeCompteur(TypeCompteur.H);
                sousFamille.setConsommationHMax(5 + i * 3);
            } else if (i % 3 == 2) {
                sousFamille.setTypeCompteur(TypeCompteur.KM);
                sousFamille.setConsommationKmMax(20 + i * 10);
			}
			sousFamilleRepo.save(sousFamille);

		}
		log.info(n + " SousFamille Loaded *****");
	}
	private void loadEngins(int n) {
        for (int i = 0; i < n; i++) {
            //Getting groupe and sous-famille
            Groupe groupe = groupeRepo.getOne(i / 9 + 1L);
            //Creation the object
            Engin engin = new Engin();
            engin.setCode("Pelle" + (i + 1));
            engin.setNumeroSerie("TPF" + i + "zz" + i);
            engin.setGroupe(groupe);
            engin.setSousFamille(sousFamilleRepo.getOne(i / 4 + 1L));
            engin.setConsommationMoyenne(6f);
            if (i % 3 == 0) {
                engin.setCompteurInitialH(1000 + (i * 10));
                engin.setCompteurInitialKm(1000 + (i * 10));
                engin.setObjectif(11);
                engin.setPrixLocationJournalier(1000 + i * 10);
            } else if (i % 3 == 1) {
                engin.setCompteurInitialH(1000 + (i * 10));
                engin.setObjectif(9 + i / 10);
                engin.setPrixLocationJournalier(1500 + i * 10);
            } else if (i % 3 == 2) {
                engin.setCompteurInitialKm(1000 + (i * 10));
                engin.setObjectif(12 - i / 20);
                engin.setPrixLocationJournalier(2000 + i * 10);
            }
            //Persisting
            enginRepo.save(engin);
        }
        log.info(n + " Engin Loaded *****");
    }

    private void loadFournisseur(int n) {
        for (int i = 0; i < n; i++) {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setNom("fournisseurs" + (i + 1));
            fournisseurRepo.save(fournisseur);
        }
        log.info(n + " Fournisseur Loaded *****");
    }

    private void loadEmploye(int n) {
        for (int i = 0; i < n; i++) {
            Employe employe = new Employe();
            employe.setNom("employe" + (i + 1));
            employe.setMetier("metier" + (i + 1));
            employeRepo.save(employe);
        }
        log.info(n + " Employe Loaded *****");
    }

    private void loadBonEngin(int n) {
        for (int i = 0; i < n; i++) {
            BonEngin bonEngin = new BonEngin();
            bonEngin.setDate(LocalDate.of(2018, LocalDate.now().getMonth().getValue(),
                    new Random().nextInt(28) + 1));
            bonEngin.setCode("code " + i);
            bonEngin.setCompteurHenPanne(i % 2 == 0);
            bonEngin.setCompteurH((long) i);
            bonEngin.setCompteurKmenPanne(i % 2 == 1);
            bonEngin.setCompteurKm((long) i);
            bonEngin.setQuantite(200 + i * 10);
            bonEngin.setPlein(true);
            bonEngin.setConsommationKm(i * 100f);
            bonEngin.setConsommationH(i * 1500f);
            bonEngin.setCompteurAbsoluH(i + 502L);
            bonEngin.setCompteurAbsoluKm(i + 600L);
            bonEngin.setNbrHeures(i + 2L);
            bonEngin.setEngin(enginRepo.getOne(i / 26 + 1L));
            bonEngin.setChargeHoraire(bonEngin.getNbrHeures() * bonEngin.getEngin().getPrixLocationJournalier() / bonEngin.getEngin().getObjectif());
            bonEngin.setConsommationPrevu(bonEngin.getEngin().getConsommationMoyenne().longValue() * bonEngin.getNbrHeures());
            bonEngin.setPompiste(employeRepo.getOne(i / 100 + 1L));
            bonEngin.setChauffeur(employeRepo.getOne(i / 50 + 2L));
            bonEngin.setChantierGazoil(chantierRepo.getOne(i / 100 + 1L));
            bonEngin.setChantierTravail(chantierRepo.getOne(i / 100 + 1L));
            bonEnginRepo.save(bonEngin);
        }
    }

    private void loadBonLivraison(int i) {
        for (int j = 0; j < i; j++) {
            BonLivraison bonLivraison = new BonLivraison();
            bonLivraison.setCode("Code " + (j + 1));
            bonLivraison.setDate(LocalDate.now());
            bonLivraison.setQuantite(10090);
            bonLivraison.setChantierArrivee(chantierRepo.getOne(j % 3L + 2));
            bonLivraison.setChantierDepart(chantierRepo.getOne(j % 3L + 1));
            bonLivraison.setPompiste(employeRepo.getOne(j % 3L + 2));
            bonLivraison.setTransporteur(employeRepo.getOne(j % 3L + 1));
            bonLivraisonRepo.save(bonLivraison);
        }
    }

    private void loadBonFournisseur(int n) {
        for (int i = 0; i < n; i++) {
            BonFournisseur bonFournisseur = new BonFournisseur();
            bonFournisseur.setCode("Code " + (i + 1));
            bonFournisseur.setDate(LocalDate.now());
            bonFournisseur.setPrixUnitaire(6f + i / 12);
            bonFournisseur.setQuantite(n + i);
            bonFournisseur.setChantier(chantierRepo.getOne(i / 10 + 1L));
            bonFournisseur.setFournisseur(fournisseurRepo.getOne(i / 10 + 1L));
            bonFournisseurRepo.save(bonFournisseur);
        }
    }

}
