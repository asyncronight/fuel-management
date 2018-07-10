package me.kadarh.mecaworks.controller.user.gazoil;

import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import me.kadarh.mecaworks.service.bons.BonFilterService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 10/07/18
 */
@Controller
@RequestMapping("/user/gazoil")

public class FilterController {
    private final BonFilterService bonFilterService;

    public FilterController(BonFilterService bonFilterService) {
        this.bonFilterService = bonFilterService;
    }

    @GetMapping("/bons")
    public String home(Model model, Pageable pageable, @RequestParam(defaultValue = "") String chantierD,
                       @RequestParam(defaultValue = "") String chantierA,
                       @RequestParam(defaultValue = "") String engin,
                       @RequestParam(defaultValue = "") String famille,
                       @RequestParam(defaultValue = "") String sousFamille,
                       @RequestParam(defaultValue = "") String classe,
                       @RequestParam(defaultValue = "") String groupe,
                       @RequestParam(defaultValue = "") String marque,
                       @RequestParam(defaultValue = "") String chauffeur,
                       @RequestParam(defaultValue = "") String pompiste,
                       @RequestParam(defaultValue = "") String dateFrom,
                       @RequestParam(defaultValue = "") String dateTo) {
        BonEnginDto bonEnginDto = bonFilterService.createBonDto(chantierD, chantierA, engin, famille, sousFamille, classe, groupe,
                marque, chauffeur, pompiste, dateFrom, dateTo);

        model.addAttribute("bons",
                bonFilterService.filterBonEngin(pageable, bonEnginDto));
        return "user/gazoil/bons";
    }


}
