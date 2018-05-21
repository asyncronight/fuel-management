package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Famille;
import me.kadarh.mecaworks.service.ClasseService;
import me.kadarh.mecaworks.service.FamilleService;
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
@RequestMapping("/admin/familles")
public class FamilleController {

    private final FamilleService familleService;
    private final ClasseService classeService;

    public FamilleController(FamilleService familleService, ClasseService classeService) {
        this.familleService = familleService;
        this.classeService = classeService;
    }

    @GetMapping("")
    public String index() {
        return "redirect:/admin/familles/add";
    }

    @GetMapping("/add")
    public String add(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("classes", classeService.list());
        model.addAttribute("famille", new Famille());
        model.addAttribute("page", familleService.familleList(pageable, search));
        model.addAttribute("search", search);
        return "admin/familles/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search, @Valid Famille famille, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("classes", classeService.list());
            model.addAttribute("page", familleService.familleList(pageable, search));
            model.addAttribute("search", search);
            return "admin/familles/add";
        }
        familleService.add(famille);
        return "redirect:/admin/familles/add#hrTable";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("classes", classeService.list());
        model.addAttribute("famille", familleService.get(id));
        model.addAttribute("page", familleService.familleList(pageable, search));
        model.addAttribute("edit", true);
        model.addAttribute("search", search);
        return "admin/familles/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid Famille famille, BindingResult result, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        if (result.hasErrors()) {
            model.addAttribute("classes", classeService.list());
            model.addAttribute("edit", true);
            model.addAttribute("page", familleService.familleList(pageable, search));
            model.addAttribute("search", search);
            return "admin/familles/add";
        }
        familleService.update(famille);
        return "redirect:/admin/familles/add#hrTable";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        familleService.delete(id);
        return "redirect:/admin/familles/add#hrTable";
    }

}
