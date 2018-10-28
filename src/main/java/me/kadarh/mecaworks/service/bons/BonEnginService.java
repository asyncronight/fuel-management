package me.kadarh.mecaworks.service.bons;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 24/06/18
 */
public interface BonEnginService {

	BonEngin add(BonEngin bonEngin);

	Page<BonEngin> getPage(Pageable pageable, String search);

	void delete(Long id);

	boolean hasErrors(BonEngin bon);

	boolean hasErrorsAttention(BonEngin bonEngin);
}
