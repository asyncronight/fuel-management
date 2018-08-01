package me.kadarh.mecaworks.controller.user.filter;

import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.domain.dtos.BonLivraisonDto;
import me.kadarh.mecaworks.domain.others.Chantier;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.EmployeService;
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
 * Created at 21/07/18
 */
@Controller
@RequestMapping("/user/filter")
public class FilterBonsLivraisonController {

    private final BonFilterService bonFilterService;
    private final ChantierService chantierService;
    private final EmployeService employeService;

    public FilterBonsLivraisonController(BonFilterService bonFilterService, ChantierService chantierService, EmployeService employeService) {
        this.bonFilterService = bonFilterService;
        this.chantierService = chantierService;
        this.employeService = employeService;
    }

    @GetMapping("/bl")
    public String bons(Model model) {
        List<Chantier> list = chantierService.getList();
        model.addAttribute("chantiers", list);
        model.addAttribute("employes", employeService.getList());
        BonLivraisonDto bonLivraisonDto = new BonLivraisonDto();
        bonLivraisonDto.setChantierDepart(list.size() != 0 ? list.get(0).getNom() : "");

        List<BonLivraison> bonLivraisons = bonFilterService.filterBonLivraison(bonLivraisonDto);
        model.addAttribute("page", bonLivraisons);
        model.addAttribute("bonLivraisonDto", bonLivraisonDto);
        return "user/filter/bonLivraisons";
    }

    @PostMapping("/bl")
    public String bonsPost(Model model, @Valid BonLivraisonDto bonLivraisonDto) {
        List<Chantier> list = chantierService.getList();
        model.addAttribute("chantiers", list);
        model.addAttribute("employes", employeService.getList());
        List<BonLivraison> bonLivraisons = bonFilterService.filterBonLivraison(bonLivraisonDto);
        model.addAttribute("page", bonLivraisons);
        model.addAttribute("bonLivraisonDto", bonLivraisonDto);
        return "user/filter/bonLivraisons";
    }
}
