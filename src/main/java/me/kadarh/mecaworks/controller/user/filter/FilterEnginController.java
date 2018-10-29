package me.kadarh.mecaworks.controller.user.filter;

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

@Controller
@RequestMapping("/user/filter/engins")
public class FilterEnginController {

    private final BonFilterService bonFilterService;
    private final ChantierService chantierService;
    private final EnginService enginService;
    private final FamilleService familleService;
    private final SousFamilleService sousFamilleService;
    private final ClasseService classeService;
    private final GroupeService groupeService;
    private final MarqueService marqueService;
    private final EmployeService employeService;

    public FilterEnginController(BonFilterService bonFilterService, ChantierService chantierService, EnginService enginService, FamilleService familleService, SousFamilleService sousFamilleService, ClasseService classeService, GroupeService groupeService, MarqueService marqueService, EmployeService employeService) {
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

    @GetMapping
    public String home(Model model) {
        fillModel(model);

        BonEnginDto bonEnginDto = new BonEnginDto();
        List<Chantier> list = chantierService.getList();
        bonEnginDto.setChantierArrivee(list.size() != 0 ? list.get(0).getNom() : "");
        model.addAttribute("bonEnginDto", bonEnginDto);

        model.addAttribute("page", bonFilterService.filterEngins(bonEnginDto));
        return "user/filter/engins";
    }

    @PostMapping
    public String index(Model model, @Valid BonEnginDto bonEnginDto) {
        fillModel(model);

        model.addAttribute("page", bonFilterService.filterEngins(bonEnginDto));
        return "user/filter/engins";
    }

    private void fillModel(Model model) {
        model.addAttribute("chantiers", chantierService.getList());
        model.addAttribute("engins", enginService.getList());
        model.addAttribute("classes", classeService.list());
        model.addAttribute("groupes", groupeService.list());
        model.addAttribute("marques", marqueService.list());
        model.addAttribute("familles", familleService.list());
        model.addAttribute("sousFamilles", sousFamilleService.list());
        model.addAttribute("employes", employeService.getList());
    }
}
