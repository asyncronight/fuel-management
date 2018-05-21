package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Employe;
import me.kadarh.mecaworks.domain.others.Metier;
import me.kadarh.mecaworks.service.EmployeService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * PROJECT mecaworks
 *
 * Created at 20/02/18
 * @author kadarH
 * @author salah3x
 */

@Controller
@RequestMapping("/admin/employes")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping("")
    public String list(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("page", employeService.employesList(pageable, search));
        model.addAttribute("search", search);
        return "admin/employes/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("metiers", Metier.values());
        return "admin/employes/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, @Valid Employe employe, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("metiers", Metier.values());
            return "admin/employes/add";
        }
        employeService.add(employe);
        return "redirect:/admin/employes";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("employe", employeService.get(id));
        model.addAttribute("metiers", Metier.values());
        model.addAttribute("edit", true);
        return "admin/employes/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid Employe employe, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("metiers", Metier.values());
            model.addAttribute("edit", true);
            return "admin/employes/add";
        }
        employeService.update(employe);
        return "redirect:/admin/employes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        employeService.delete(id);
        return "redirect:/admin/employes";
    }

}
