package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.SousFamille;
import me.kadarh.mecaworks.domain.others.TypeCompteur;
import me.kadarh.mecaworks.service.FamilleService;
import me.kadarh.mecaworks.service.MarqueService;
import me.kadarh.mecaworks.service.SousFamilleService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 20/02/18
 */

@Controller
@RequestMapping("/admin/sousFamilles")
public class SousFamilleController {

    private final SousFamilleService sousFamilleService;
    private final MarqueService marqueService;
    private final FamilleService familleService;

    public SousFamilleController(SousFamilleService sousFamilleService, MarqueService marqueService, FamilleService familleService) {
        this.sousFamilleService = sousFamilleService;
        this.marqueService = marqueService;
        this.familleService = familleService;
    }

    @GetMapping("/add")
    public String add(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("sousFamille", new SousFamille());
        model.addAttribute("page", sousFamilleService.sousFamilleList(pageable, search));
        model.addAttribute("familles", familleService.familleList(pageable, ""));
        model.addAttribute("marques", marqueService.marqueList(pageable, ""));
        model.addAttribute("typesCompteur", TypeCompteur.values());
        model.addAttribute("search", search);
        return "admin/sousFamilles/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search, @Valid SousFamille sousFamille, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("page", sousFamilleService.sousFamilleList(pageable, search));
            model.addAttribute("familles", familleService.familleList(pageable, ""));
            model.addAttribute("marques", marqueService.marqueList(pageable, ""));
            model.addAttribute("typesCompteur", TypeCompteur.values());
            model.addAttribute("search", search);
            return "admin/sousFamilles/add";
        }
        sousFamilleService.add(sousFamille);
        return "redirect:/admin/sousFamilles/add#hrTable";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("sousFamille", sousFamilleService.get(id));
        model.addAttribute("page", sousFamilleService.sousFamilleList(pageable, search));
        model.addAttribute("familles", familleService.familleList(pageable, ""));
        model.addAttribute("marques", marqueService.marqueList(pageable, ""));
        model.addAttribute("typesCompteur", TypeCompteur.values());
        model.addAttribute("edit", true);
        model.addAttribute("search", search);
        return "admin/sousFamilles/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid SousFamille sousFamille, BindingResult result, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("page", sousFamilleService.sousFamilleList(pageable, search));
            model.addAttribute("familles", familleService.familleList(pageable, ""));
            model.addAttribute("marques", marqueService.marqueList(pageable, ""));
            model.addAttribute("typesCompteur", TypeCompteur.values());
            model.addAttribute("search", search);
            return "admin/sousFamilles/add";
        }
        sousFamilleService.update(sousFamille);
        return "redirect:/admin/sousFamilles/add#hrTable";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        //sousFamilleService.delete(id);
        return "redirect:/admin/sousFamilles/add#hrTable";
    }

}
