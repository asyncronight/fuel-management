package me.kadarh.mecaworks.service.impl.bons;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import me.kadarh.mecaworks.domain.others.*;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.others.*;
import me.kadarh.mecaworks.service.bons.BonFilterService;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public Page<BonEngin> filterBonEngin(Pageable pageable, BonEnginDto bonEnginDto) {
        try {
            String famille = bonEnginDto.getFamille();
            String classe = bonEnginDto.getClasse();
            String engin = bonEnginDto.getCodeEngin();
            String sousFamille = bonEnginDto.getSousFamille();
            String groupe = bonEnginDto.getGroupe();
            String marque = bonEnginDto.getMarque();
            String chantierDepart = bonEnginDto.getChantierDepart();
            String chantierArrivee = bonEnginDto.getChantierArrivee();

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

            Employe employe = new Employe();
            employe.setNom(null);

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

            bonEngin.setPompiste(employe);
            bonEngin.setChauffeur(employe);
            bonEngin.setEngin(engin1);
            bonEngin.setChantierGazoil(chantierDepart1);
            bonEngin.setChantierTravail(chantierArrivee1);

            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase()
                    .withIgnoreNullValues();
            Example<BonEngin> example = Example.of(bonEngin, matcher);
            log.debug("getting search results");
            Page<BonEngin> page = bonEnginRepo.findAll(example, pageable);
            try {
                page = new PageImpl<>(
                        page.getContent().stream().filter(bonEngin1 -> bonEngin1.getDate().isBefore(LocalDate.parse(bonEnginDto.getDateTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        ).filter(bonEngin1 -> bonEngin1.getDate().isAfter(LocalDate.parse(bonEnginDto.getDateFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        ).collect(Collectors.toList()), pageable, page.getTotalElements()
                );
                log.debug("filter by dates successfully");
            } catch (Exception e) {
                log.debug("Cannot search by date : keyword doesn't match date pattern");
            }
            return page;

        } catch (Exception e) {
            log.debug("Failed retrieving list of bons");
            throw new OperationFailedException("Operation échouée", e);
        }
    }

    @Override
    public BonEnginDto createBonDto(String chantierD, String chantierA, String engin, String famille, String sousFamille, String classe, String groupe, String marque, String chauffeur, String pompiste, String dateFrom, String dateTo) {
        BonEnginDto bonEnginDto = new BonEnginDto();
        bonEnginDto.setChantierDepart(chantierD);
        bonEnginDto.setChantierArrivee(chantierA);
        bonEnginDto.setCodeEngin(engin);
        bonEnginDto.setFamille(famille);
        bonEnginDto.setSousFamille(sousFamille);
        bonEnginDto.setClasse(classe);
        bonEnginDto.setGroupe(groupe);
        bonEnginDto.setMarque(marque);
        bonEnginDto.setChauffeur(chauffeur);
        bonEnginDto.setPompiste(pompiste);
        bonEnginDto.setDateFrom(dateFrom);
        bonEnginDto.setDateTo(dateTo);
        return bonEnginDto;
    }
}
