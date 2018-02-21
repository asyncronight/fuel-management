package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Fournisseur;
import me.kadarh.mecaworks.service.FournisseurService;
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
 * Created at 21/02/18
 */

@Controller
@RequestMapping("/admin/fournisseurs")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping("")
    public String list(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("page", fournisseurService.fournisseurList(pageable, search));
        model.addAttribute("search", search);
        return "admin/fournisseurs/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("fournisseur", new Fournisseur());
        return "admin/fournisseurs/add";
    }

    @PostMapping("/add")
    public String addPost(@Valid Fournisseur fournisseur, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/fournisseurs/add";
        }
        fournisseurService.add(fournisseur);
        return "redirect:/admin/fournisseurs";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("fournisseur", fournisseurService.get(id));
        model.addAttribute("edit", true);
        return "admin/fournisseurs/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid Fournisseur fournisseur, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            return "admin/fournisseurs/add";
        }
        fournisseurService.update(fournisseur);
        return "redirect:/admin/fournisseurs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return "redirect:/admin/fournisseurs";
    }

}
