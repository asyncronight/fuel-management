package me.kadarh.mecaworks.repo.alertes;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.others.TypeAlerte;
import me.kadarh.mecaworks.repo.others.TypeAlerteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author kadarH
 */

@Repository
@Slf4j
public class DataFakerA {

    @Autowired
    private TypeAlerteRepo typeAlerteRepo;

    public void run() throws Exception {
        log.info("This is DataFaker Of Alertes");
        loadTypeAlerte();
    }

    // Loading
    private void loadTypeAlerte() {
        TypeAlerte type1 = new TypeAlerte();
        type1.setNom("Consommation");
        TypeAlerte type2 = new TypeAlerte();
        type2.setNom("compteur attention");
        typeAlerteRepo.save(type1);
        typeAlerteRepo.save(type2);
        log.info("**** typeAlerte Loaded *****");
    }

}
