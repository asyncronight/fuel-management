package me.kadarh.mecaworks.controller.user.filter;

import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.domain.dtos.BonFournisseurDto;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.FournisseurService;
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
public class FilterBonsFournisseurController {

    private final BonFilterService bonFilterService;
    private final ChantierService chantierService;
    private final FournisseurService fournisseurService;

    public FilterBonsFournisseurController(BonFilterService bonFilterService, ChantierService chantierService, FournisseurService fournisseurService) {
        this.bonFilterService = bonFilterService;
        this.chantierService = chantierService;
        this.fournisseurService = fournisseurService;
    }

    @GetMapping("/bf")
    public String bons(Model model) {
        model.addAttribute("chantiers", chantierService.getList());
        model.addAttribute("fournisseurs", fournisseurService.getList());
        BonFournisseurDto bonFournisseurDto = new BonFournisseurDto();
        List<BonFournisseur> bonFournisseurs = bonFilterService.filterBonFournisseur(bonFournisseurDto);
        model.addAttribute("page", bonFournisseurs);
        model.addAttribute("bonFournisseurDto", bonFournisseurDto);
        return "user/filter/bonFournisseurs";
    }

    @PostMapping("/bf")
    public String bonsPost(Model model, @Valid BonFournisseurDto bonFournisseurDto) {
        model.addAttribute("chantiers", chantierService.getList());
        model.addAttribute("fournisseurs", fournisseurService.getList());
        List<BonFournisseur> bonFournisseurs = bonFilterService.filterBonFournisseur(bonFournisseurDto);
        model.addAttribute("page", bonFournisseurs);
        model.addAttribute("bonFournisseurDto", bonFournisseurDto);
        return "user/filter/bonFournisseurs";
    }
}
