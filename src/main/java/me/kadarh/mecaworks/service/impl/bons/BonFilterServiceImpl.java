package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import me.kadarh.mecaworks.domain.dtos.BonFournisseurDto;
import me.kadarh.mecaworks.domain.dtos.BonLivraisonDto;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.bons.BonFournisseurRepo;
import me.kadarh.mecaworks.repo.bons.BonLivraisonRepo;
import me.kadarh.mecaworks.service.bons.BonFilterService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 24/06/18
 */

@Service
@Transactional
@Slf4j
public class BonFilterServiceImpl implements BonFilterService {

    private final BonLivraisonRepo bonLivraisonRepo;
    private final BonEnginRepo bonEnginRepo;
    private final BonFournisseurRepo bonFournisseurRepo;

    public BonFilterServiceImpl(BonLivraisonRepo bonLivraisonRepo, BonEnginRepo bonEnginRepo, BonFournisseurRepo bonFournisseurRepo) {
        this.bonLivraisonRepo = bonLivraisonRepo;
        this.bonEnginRepo = bonEnginRepo;
        this.bonFournisseurRepo = bonFournisseurRepo;
    }

    @Override
    public List<BonEngin> filterBonEngin(BonEnginDto bonEnginDto) {
        try {
            String famille = bonEnginDto.getFamille().equals("") ? null : bonEnginDto.getFamille();
            String classe = bonEnginDto.getClasse().equals("") ? null : bonEnginDto.getClasse();
            String engin = bonEnginDto.getCodeEngin().equals("") ? null : bonEnginDto.getCodeEngin();
            String sousFamille = bonEnginDto.getSousFamille().equals("") ? null : bonEnginDto.getSousFamille();
            String groupe = bonEnginDto.getGroupe().equals("") ? null : bonEnginDto.getGroupe();
            String marque = bonEnginDto.getMarque().equals("") ? null : bonEnginDto.getMarque();
            String chantierDepart = bonEnginDto.getChantierDepart().equals("") ? null : bonEnginDto.getChantierDepart();
            String chantierArrivee = bonEnginDto.getChantierArrivee().equals("") ? null : bonEnginDto.getChantierArrivee();
            String chauffeur = bonEnginDto.getChauffeur().equals("") ? null : bonEnginDto.getChauffeur();
            String pompiste = bonEnginDto.getPompiste().equals("") ? null : bonEnginDto.getPompiste();
            String locataire = bonEnginDto.getLocataire().equals("") ? "all" : bonEnginDto.getLocataire();

            BonEngin bonEngin = new BonEngin();
            bonEngin.setCode(null);
            bonEngin.setCompteurAbsoluKm(null);
            bonEngin.setCompteurKm(null);
            bonEngin.setCompteurH(null);
            bonEngin.setCompteurAbsoluH(null);
            bonEngin.setConsommationH(null);
            bonEngin.setConsommationKm(null);
            bonEngin.setQuantite(null);
            bonEngin.setCarburant(null);
            bonEngin.setCompteurPompe(null);
            bonEngin.setCompteurHenPanne(null);
            bonEngin.setCompteurKmenPanne(null);
            bonEngin.setNbrHeures(null);
            bonEngin.setConsommationPrevu(null);
            bonEngin.setChargeHoraire(null);

            Employe chauf = new Employe();
            chauf.setNom(chauffeur);
            Employe pomp = new Employe();
            pomp.setNom(pompiste);

            Engin engin1 = new Engin();
            engin1.setCode(engin);
            engin1.setObjectif(null);
            engin1.setConsommationMoyenne(null);

            SousFamille sousFamille1 = new SousFamille();
            sousFamille1.setNom(sousFamille);

            Famille famille1 = new Famille();
            famille1.setNom(famille);

            Groupe groupe1 = new Groupe();
            groupe1.setNom(groupe);

            Chantier chantierDepart1 = new Chantier();
            Chantier chantierArrivee1 = new Chantier();
            chantierDepart1.setNom(chantierDepart);
            chantierArrivee1.setNom(chantierArrivee);

            Marque marque1 = new Marque();
            marque1.setNom(marque);

            Classe classe1 = new Classe();
            classe1.setNom(classe);

            famille1.setClasse(classe1);
            sousFamille1.setFamille(famille1);
            sousFamille1.setMarque(marque1);

            engin1.setSousFamille(sousFamille1);
            engin1.setGroupe(groupe1);

            bonEngin.setPompiste(pomp);
            bonEngin.setChauffeur(chauf);
            bonEngin.setEngin(engin1);
            bonEngin.setChantierGazoil(chantierDepart1);
            bonEngin.setChantierTravail(chantierArrivee1);

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                    .withIgnoreCase()
                    .withIgnoreNullValues();
            Example<BonEngin> example = Example.of(bonEngin, matcher);
            log.debug("getting search results");

            List<BonEngin> page = bonEnginRepo.findAll(example);
            if (locataire.equals("oui"))
                page = page.stream().filter(bonEngin1 -> bonEngin1.getEngin().getGroupe().getLocataire()).collect(Collectors.toList());
            else if (locataire.equals("non"))
                page = page.stream().filter(bonEngin1 -> !bonEngin1.getEngin().getGroupe().getLocataire()).collect(Collectors.toList());

            try {
                page = page.stream().filter(bonEngin1 -> bonEngin1.getDate().isBefore(LocalDate.parse(bonEnginDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        || bonEngin1.getDate().isEqual(LocalDate.parse(bonEnginDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                        .filter(bonEngin1 -> bonEngin1.getDate().isAfter(LocalDate.parse(bonEnginDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                || bonEngin1.getDate().isEqual(LocalDate.parse(bonEnginDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        ).collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                return page;
            }
            log.debug("filter by dates successfully");
            return page;
        } catch (Exception e) {
            log.debug("Failed retrieving list of bons Engins");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    @Override
    public List<BonEngin> filterBonEngin(boolean groupbyEngin) {
        try {
            //Todo @salah : implement this method ( 22 / 10 / 2018 )
            //Todo : mal9itch kifach ndir findAll group by ou ndwzliha Example
            //Filters dirhom b datatable() rah zebdawiyin
            return bonEnginRepo.findAllGroupByEngin();
        } catch (Exception e) {
            log.debug("Failed retrieving list of bons Engins");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    @Override
    public List<BonLivraison> filterBonLivraison(BonLivraisonDto bonLivraisonDto) {
        try {
            String chantier_Depart = bonLivraisonDto.getChantierDepart().equals("") ? null : bonLivraisonDto.getChantierDepart();
            String chantier_Arrivee = bonLivraisonDto.getChantierArrivee().equals("") ? null : bonLivraisonDto.getChantierArrivee();
            String transporteur = bonLivraisonDto.getTransporteur().equals("") ? null : bonLivraisonDto.getTransporteur();
            String pompiste = bonLivraisonDto.getPompiste().equals("") ? null : bonLivraisonDto.getPompiste();

            BonLivraison bonLivraison = new BonLivraison();
            bonLivraison.setQuantite(null);

            Chantier chantierDepart = new Chantier();
            chantierDepart.setNom(chantier_Depart);
            chantierDepart.setStock(null);

            Chantier chantierArrivee = new Chantier();
            chantierArrivee.setNom(chantier_Arrivee);
            chantierArrivee.setStock(null);

            Employe trans = new Employe();
            trans.setNom(transporteur);
            Employe pompist = new Employe();
            pompist.setNom(pompiste);

            bonLivraison.setChantierDepart(chantierDepart);
            bonLivraison.setChantierArrivee(chantierArrivee);
            bonLivraison.setPompiste(pompist);
            bonLivraison.setTransporteur(trans);

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                    .withIgnoreCase()
                    .withIgnoreNullValues();
            Example<BonLivraison> example = Example.of(bonLivraison, matcher);
            log.debug("getting search results");

            List<BonLivraison> page = bonLivraisonRepo.findAll(example);
            try {
                page = page.stream().filter(bonLivraison1 -> bonLivraison1.getDate().isBefore(LocalDate.parse(bonLivraisonDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        || bonLivraison1.getDate().isEqual(LocalDate.parse(bonLivraisonDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                        .filter(bonLivraison1 -> bonLivraison1.getDate().isAfter(LocalDate.parse(bonLivraisonDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                || bonLivraison1.getDate().isEqual(LocalDate.parse(bonLivraisonDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        ).collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                return page;
            }
            log.debug("filter by dates successfully");
            return page;
        } catch (Exception e) {
            log.debug("Failed retrieving list of bons");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    @Override
    public List<BonFournisseur> filterBonFournisseur(BonFournisseurDto bonFournisseurDto) {
        try {
            String ch = bonFournisseurDto.getChantier().equals("") ? null : bonFournisseurDto.getChantier();
            String fournisseur = bonFournisseurDto.getFournisseur().equals("") ? null : bonFournisseurDto.getFournisseur();

            BonFournisseur bonFournisseur = new BonFournisseur();
            bonFournisseur.setQuantite(null);

            Chantier chantier = new Chantier();
            chantier.setNom(ch);
            chantier.setStock(null);

            Fournisseur fournisseur1 = new Fournisseur();
            fournisseur1.setNom(fournisseur);

            bonFournisseur.setChantier(chantier);
            bonFournisseur.setFournisseur(fournisseur1);

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                    .withIgnoreCase()
                    .withIgnoreNullValues();
            Example<BonFournisseur> example = Example.of(bonFournisseur, matcher);
            log.debug("getting search results");

            List<BonFournisseur> page = bonFournisseurRepo.findAll(example);
            try {
                page = page.stream().filter(bonFournisseur1 -> bonFournisseur1.getDate().isBefore(LocalDate.parse(bonFournisseurDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        || bonFournisseur1.getDate().isEqual(LocalDate.parse(bonFournisseurDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                        .filter(bonFournisseur1 -> bonFournisseur1.getDate().isAfter(LocalDate.parse(bonFournisseurDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                || bonFournisseur1.getDate().isEqual(LocalDate.parse(bonFournisseurDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        ).collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                return page;
            }
            log.debug("filter by dates successfully");
            return page;
        } catch (Exception e) {
            log.debug("Failed retrieving list of bons Fournisseur");
            throw new OperationFailedException("Operation échouée", e);
        }
    }
}
