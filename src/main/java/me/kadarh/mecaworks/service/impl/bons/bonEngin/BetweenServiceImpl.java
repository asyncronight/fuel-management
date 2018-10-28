package me.kadarh.mecaworks.service.impl.bons.bonEngin;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.service.bons.BetweenStockAndBonLivraisonService;
import me.kadarh.mecaworks.service.bons.BonLivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 02/11/18
 */
@Service
@Transactional
public class BetweenServiceImpl implements BetweenStockAndBonLivraisonService {

    private BonLivraisonService bonLivraisonService;

    @Autowired
    public void setBonLivraisonService(BonLivraisonService bonLivraisonService) {
        this.bonLivraisonService = bonLivraisonService;
    }

    @Override
    public void insertBonLivraison(BonEngin bonEngin) {
        bonLivraisonService.insertBonLivraison(bonEngin);
    }
}
