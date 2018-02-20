package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.service.EmployeService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 20/02/18
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


}
