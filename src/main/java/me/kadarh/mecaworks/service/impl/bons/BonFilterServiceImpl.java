package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.others.*;
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

    private final EnginRepo enginRepo;
    private final FamilleRepo familleRepo;
    private final ClasseRepo classeRepo;
    private final SousFamilleRepo sousFamilleRepo;
    private final ChantierRepo chantierRepo;

    private final BonEnginRepo bonEnginRepo;

    public BonFilterServiceImpl(EnginRepo enginRepo, FamilleRepo familleRepo, ClasseRepo classeRepo, SousFamilleRepo sousFamilleRepo, ChantierRepo chantierRepo, BonEnginRepo bonEnginRepo) {
        this.enginRepo = enginRepo;
        this.familleRepo = familleRepo;
        this.classeRepo = classeRepo;
        this.sousFamilleRepo = sousFamilleRepo;
        this.chantierRepo = chantierRepo;
        this.bonEnginRepo = bonEnginRepo;
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
            log.debug("Failed retrieving list of bons");
            throw new OperationFailedException("Operation échouée", e);
        }
    }
}
