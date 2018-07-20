package me.kadarh.mecaworks.controller.saisie;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.domain.bons.Carburant;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.EmployeService;
import me.kadarh.mecaworks.service.EnginService;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by salah on 3/5/18 10:15 PM.
 */
@Controller
@Slf4j
@RequestMapping("/saisi/engins")
public class BonEnginController {

	private final BonEnginService bonEnginService;
	private final EmployeService employeService;
	private final ChantierService chantierService;
	private final EnginService enginService;

    public BonEnginController(BonEnginService bonEnginService, EmployeService employeService, ChantierService chantierService, EnginService enginService) {
        this.bonEnginService = bonEnginService;
        this.employeService = employeService;
		this.chantierService = chantierService;
		this.enginService = enginService;
    }

	@GetMapping("")
	public String list(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
		model.addAttribute("page", bonEnginService.getPage(pageable, search));
		model.addAttribute("search", search);
		return "saisi/engins/list";
	}

	@GetMapping("/add")
	public String addGet(Model model) {
		model.addAttribute("bonEngin", new BonEngin());
		model.addAttribute("chantiers", chantierService.getList());
		model.addAttribute("engins", enginService.getList());
		model.addAttribute("employes", employeService.getList());
		model.addAttribute("carburants", Carburant.values());
		return "saisi/engins/add";
	}

	@PostMapping("/add")
	public String add(Model model, @Valid BonEngin bonEngin, BindingResult result) {
        boolean error = false;
        if (result.hasErrors() || (error = bonEnginService.hasErrors(bonEngin))) {
            model.addAttribute("hasError", error);
			model.addAttribute("chantiers", chantierService.getList());
			model.addAttribute("engins", enginService.getList());
			model.addAttribute("employes", employeService.getList());
			model.addAttribute("carburants", Carburant.values());
			return "saisi/engins/add";
		} else if (bonEnginService.hasErrorsAttention(bonEngin)) {
			model.addAttribute("bonEngin", bonEngin);
			return "saisi/engins/confirm";
		}
		bonEnginService.add(bonEngin);
		return "redirect:/saisi/engins";
	}

	@PostMapping("/confirm")
	public String confirm(@Valid BonEngin bonEngin, BindingResult result) {
		if (result.hasErrors()) {
			return "saisi/engins/confirm";
		}
		bonEnginService.add(bonEngin);
		return "redirect:/saisi/engins";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		bonEnginService.delete(id);
		return "redirect:/saisi/engins";
	}
}
