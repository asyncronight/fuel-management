package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.dtos.BonEnginDto;

import java.util.List;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 24/06/18
 */
public interface BonFilterService {

    //Todo @salah use this function for filter
    List<BonEngin> filterBonEngin(BonEnginDto bonEnginDto);

}
