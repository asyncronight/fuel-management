package me.kadarh.mecaworks.controller.user.alerte;

import me.kadarh.mecaworks.service.AlerteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 22/07/18
 */

@Controller
@RequestMapping("/user/alerte")
public class AlerteController {

    private final AlerteService alerteService;

    public AlerteController(AlerteService alerteService) {
        this.alerteService = alerteService;
    }

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("alerts", alerteService.getList());
        return "user/alerte/list";
    }

    @PostMapping("/{id}")
    public String hide(@PathVariable Long id) {
        alerteService.hideAlert(id);
        return "redirect:/user/alerte";
    }
}
