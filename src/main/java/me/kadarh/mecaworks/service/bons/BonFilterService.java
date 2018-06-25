package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 24/06/18
 */
public interface BonFilterService {

    //Todo @salah use this function for filter
    Page<BonEngin> filterBonEngin(Pageable pageable, BonEnginDto bonEnginDto);

}
