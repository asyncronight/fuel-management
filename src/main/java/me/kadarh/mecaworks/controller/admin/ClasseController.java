package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Classe;
import me.kadarh.mecaworks.service.ClasseService;
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
@RequestMapping("/admin/classes")
public class ClasseController {

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping("")
    public String index() {
        return "redirect:/admin/classes/add";
    }

    @GetMapping("/add")
    public String add(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("classe", new Classe());
        model.addAttribute("page", classeService.classeList(pageable, search));
        model.addAttribute("search", search);
        return "admin/classes/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search, @Valid Classe classe, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("page", classeService.classeList(pageable, search));
            model.addAttribute("search", search);
            return "admin/classes/add";
        }
        classeService.add(classe);
        return "redirect:/admin/classes/add#hrTable";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("classe", classeService.get(id));
        model.addAttribute("page", classeService.classeList(pageable, search));
        model.addAttribute("edit", true);
        model.addAttribute("search", search);
        return "admin/classes/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid Classe classe, BindingResult result, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("page", classeService.classeList(pageable, search));
            model.addAttribute("search", search);
            return "admin/classes/add";
        }
        classeService.update(classe);
        return "redirect:/admin/classes/add#hrTable";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
	    classeService.delete(id);
        return "redirect:/admin/classes/add#hrTable";
    }

}
