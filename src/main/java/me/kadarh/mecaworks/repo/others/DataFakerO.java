package me.kadarh.mecaworks.repo.others;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.alertes.AlerteEngin;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.repo.bons.DataFakerB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author kadarH
 */

@Component
@Transactional
@Slf4j
@Profile("dev")
public class DataFakerO implements CommandLineRunner {


	@Autowired
	private ChantierRepo chantierRepo;
	@Autowired
	private EmployeRepo employeRepo;
	@Autowired
	private GroupeRepo groupeRepo;
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
	private DataFakerB dataFakerB;
	@Autowired
	private AlerteRepo alerteRepo;

	@Override
	public void run(String... strings) {
		log.info("This is the DataFaker Of Other Domains");
		loadGroupe(5);
		loadChantiers(5);
		loadFamille(5);
		loadSousFamilles(10);
        loadMarques(5);
        loadEngins(20);
		loadFournisseur(20);
		loadAlerte(10);
		loadEmploye(20);
		dataFakerB.run();
	}

	private void loadAlerte(int n) {

		for (int i = 0; i < n; i++) {
			AlerteEngin alerteEngin = new AlerteEngin();
			alerteEngin.setDate(LocalDate.now());
			alerteEngin.setEtat(true);
			alerteEngin.setConsommationH(345 + (float) i * 3);
			alerteEngin.setConsommationKm(345000 + (float) i * 3);
			alerteRepo.save(alerteEngin);

		}
	}

	// Loading Groupes
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

	// Loading Employees
	private void loadEmploye(int n) {
		for (int i = 0; i < n; i++) {
			Employe employe = new Employe();
			employe.setNom("employe" + (i + 1));
			employeRepo.save(employe);
		}
		log.info(n + " Employe Loaded *****");
	}

    // Loading Marques
    private void loadMarques(int n) {
        for (int i = 0; i < n; i++) {
            Marque marque = new Marque();
            marque.setNom("marque" + (i + 1));
            marqueRepo.save(marque);
        }
        log.info(n + " Marque Loaded *****");
    }

	// Loading Chantiers
	private void loadChantiers(int n) {
		for (int i = 0; i < n; i++) {
			Chantier chantier = new Chantier();
			chantier.setNom("chantier" + (i + 1));
			chantier.setAdresse("Kenitra");
			chantier.setStock((i % 2) * 10);
			chantierRepo.save(chantier);
		}
		log.info(n + " Chantier Loaded *****");
	}

	// Loading Fournisseur
	private void loadFournisseur(int n) {
		for (int i = 0; i < n; i++) {
			Fournisseur fournisseur = new Fournisseur();
			fournisseur.setNom("fournisseur" + (i + 1));
			fournisseurRepo.save(fournisseur);
		}
		log.info(n + " Fournisseur Loaded *****");
	}

	// Loading Familles
	private void loadFamille(int n) {
		for (int i = 0; i < n; i++) {
			Famille famille = new Famille();
			famille.setNom("famille" + (i + 1));
			familleRepo.save(famille);
		}
		log.info(n + " Famille Loaded *****");
	}

	// Loading SousFamilles
	private void loadSousFamilles(int n) {
		for (int i = 0; i < n; i++) {
			Famille famille = familleRepo.getOne((i / 2) + 1L);
			SousFamille sousFamille = new SousFamille();
			sousFamille.setNom("sousFamille" + (i + 1));
			sousFamille.setFamille(famille);
			sousFamille.setCapaciteReservoir(10 + i * 10);
			if (i % 3 == 0) {
				sousFamille.setTypeCompteur(TypeCompteur.KM_L);
				sousFamille.setConsommationKmMax(20 + i * 10);
				sousFamille.setConsommationLMax(20 + i * 10);
			} else if (i % 3 == 1) {
				sousFamille.setTypeCompteur(TypeCompteur.L);
				sousFamille.setConsommationLMax(20 + i * 10);
			} else if (i % 3 == 2) {
				sousFamille.setTypeCompteur(TypeCompteur.KM);
				sousFamille.setConsommationKmMax(20 + i * 10);
			}
			sousFamilleRepo.save(sousFamille);

		}
		log.info(n + " SousFamille Loaded *****");
	}

	// Loading Engins
	private void loadEngins(int n) {
		for (int i = 0; i < n; i++) {
			//Getting groupe and sous-famille
			Groupe groupe = groupeRepo.getOne(i / 4 + 1L);
			SousFamille sousFamille = sousFamilleRepo.getOne(i / 2 + 1L);
            Marque marque = marqueRepo.getOne(i / 4 + 1L);
            //Creation the object
			Engin engin = new Engin();
			engin.setCode("Pelle" + (i + 1));
			engin.setNumeroSerie("TPF" + i + "zz" + i);
			engin.setGroupe(groupe);
            engin.setMarque(marque);
            engin.setSousFamille(sousFamille);
			if (i % 3 == 0) {
				engin.setCompteurInitialL(1000 + (i * 10));
				engin.setCompteurInitialKm(1000 + (i * 10));
			} else if (i % 3 == 1) {
				engin.setCompteurInitialL(1000 + (i * 10));
			} else if (i % 3 == 2) {
				engin.setCompteurInitialKm(1000 + (i * 10));
			}
			//Persisting
			enginRepo.save(engin);
		}
		log.info(n + " Engin Loaded *****");
	}


}
