package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Groupe;
import me.kadarh.mecaworks.service.GroupeService;
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
@RequestMapping("/admin/groupes")
public class GroupeController {

    private final GroupeService groupeService;

    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @GetMapping("")
    public String index() {
        return "redirect:/admin/groupes/add";
    }

    @GetMapping("/add")
    public String add(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("groupe", new Groupe());
        model.addAttribute("page", groupeService.groupesList(pageable, search));
        model.addAttribute("search", search);
        return "admin/groupes/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search, @Valid Groupe groupe, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("page", groupeService.groupesList(pageable, search));
            model.addAttribute("search", search);
            return "admin/groupes/add";
        }
        groupeService.add(groupe);
        return "redirect:/admin/groupes/add#hrTable";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        model.addAttribute("groupe", groupeService.get(id));
        model.addAttribute("page", groupeService.groupesList(pageable, search));
        model.addAttribute("edit", true);
        model.addAttribute("search", search);
        return "admin/groupes/add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(Model model, @Valid Groupe groupe, BindingResult result, Pageable pageable, @RequestParam(defaultValue = "") String search) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("page", groupeService.groupesList(pageable, search));
            model.addAttribute("search", search);
            return "admin/groupes/add";
        }
        groupeService.update(groupe);
        return "redirect:/admin/groupes/add#hrTable";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        groupeService.delete(id);
        return "redirect:/admin/groupes/add#hrTable";
    }

}
