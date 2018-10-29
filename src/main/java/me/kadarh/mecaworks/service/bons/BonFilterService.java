package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import me.kadarh.mecaworks.domain.dtos.BonFournisseurDto;
import me.kadarh.mecaworks.domain.dtos.BonLivraisonDto;

import java.util.List;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 24/06/18
 */
public interface BonFilterService {

    List<BonEngin> filterBonEngin(BonEnginDto bonEnginDto);

    List<BonEngin> filterEngins(BonEnginDto bonEnginDto);

    List<BonLivraison> filterBonLivraison(BonLivraisonDto bonLivraisonDto);

    List<BonFournisseur> filterBonFournisseur(BonFournisseurDto bonFournisseurDto);

}
