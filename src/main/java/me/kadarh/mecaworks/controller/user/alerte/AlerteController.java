package me.kadarh.mecaworks.controller.user.alerte;

import me.kadarh.mecaworks.service.AlerteService;
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
    public String index(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {

        model.addAttribute("page", alerteService.getPage(pageable, search));
        model.addAttribute("search", search);
        return "user/alerte/list";
    }
}
