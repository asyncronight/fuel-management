package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Marque;
import me.kadarh.mecaworks.service.MarqueService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * PROJECT mecaworks
 *
 * Created at 22/02/18
 * @author kadarH
 * @author salah3x
 */

@Controller
@RequestMapping("/admin/marques")
public class MarqueController {

    private final MarqueService marqueService;

    public MarqueController(MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    @GetMapping("")
    public String index() {
        return "redirect:/admin/marques/add";
    }

    @GetMapping("/add")
    public String add(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("marque", new Marque());
        model.addAttribute("page", marqueService.marqueList(pageable, search));
        model.addAttribute("search", search);
        return "admin/marques/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search, @Valid Marque marque, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("page", marqueService.marqueList(pageable, search));
            model.addAttribute("search", search);
            return "admin/marques/add";
        }
        marqueService.add(marque);
        return "redirect:/admin/marques/add#hrTable";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("marque", marqueService.get(id));
        model.addAttribute("page", marqueService.marqueList(pageable, search));
        model.addAttribute("edit", true);
        model.addAttribute("search", search);
        return "admin/marques/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid Marque marque, BindingResult result, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("page", marqueService.marqueList(pageable, search));
            model.addAttribute("search", search);
            return "admin/marques/add";
        }
        marqueService.update(marque);
        return "redirect:/admin/marques/add#hrTable";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        marqueService.delete(id);
        return "redirect:/admin/marques/add#hrTable";
    }

}
