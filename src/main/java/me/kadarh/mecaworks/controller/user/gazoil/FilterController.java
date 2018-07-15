package me.kadarh.mecaworks.controller.user.gazoil;

import me.kadarh.mecaworks.domain.dtos.BonEnginDto;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.service.*;
import me.kadarh.mecaworks.service.bons.BonFilterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

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
    private final ChantierService chantierService;
    private final EnginService enginService;
    private final FamilleService familleService;
    private final SousFamilleService sousFamilleService;
    private final ClasseService classeService;
    private final GroupeService groupeService;
    private final MarqueService marqueService;
    private final EmployeService employeService;

    public FilterController(BonFilterService bonFilterService, ChantierService chantierService, EnginService enginService, FamilleService familleService, SousFamilleService sousFamilleService, ClasseService classeService, GroupeService groupeService, MarqueService marqueService, EmployeService employeService) {
        this.bonFilterService = bonFilterService;
        this.chantierService = chantierService;
        this.enginService = enginService;
        this.familleService = familleService;
        this.sousFamilleService = sousFamilleService;
        this.classeService = classeService;
        this.groupeService = groupeService;
        this.marqueService = marqueService;
        this.employeService = employeService;
    }

    @GetMapping("/bons")
    public String home(Model model) {
        List<Chantier> list = chantierService.getList();
        model.addAttribute("chantiers", list);
        model.addAttribute("engins", enginService.getList());
        model.addAttribute("classes", classeService.list());
        model.addAttribute("groupes", groupeService.list());
        model.addAttribute("marques", marqueService.list());
        model.addAttribute("familles", familleService.list());
        model.addAttribute("sousFamilles", sousFamilleService.list());
        model.addAttribute("employes", employeService.getList());

        BonEnginDto bonEnginDto = new BonEnginDto();
        bonEnginDto.setChantierDepart(list.get(0).getNom() != null ? list.get(0).getNom() : "");
        //bonEnginDto.setChantierArrivee(list.get(0).getNom()!=null?list.get(0).getNom():"");

        model.addAttribute("page", bonFilterService.filterBonEngin(bonEnginDto));
        model.addAttribute("bonEnginDto", bonEnginDto);
        return "user/gazoil/bons";
    }

    @PostMapping("/bons")
    public String index(Model model, @Valid BonEnginDto bonEnginDto) {
        model.addAttribute("chantiers", chantierService.getList());
        model.addAttribute("engins", enginService.getList());
        model.addAttribute("classes", classeService.list());
        model.addAttribute("groupes", groupeService.list());
        model.addAttribute("marques", marqueService.list());
        model.addAttribute("familles", familleService.list());
        model.addAttribute("sousFamilles", sousFamilleService.list());
        model.addAttribute("employes", employeService.getList());
        model.addAttribute("page", bonFilterService.filterBonEngin(bonEnginDto));
        model.addAttribute("bonEnginDto", bonEnginDto);
        return "user/gazoil/bons";
    }
}
