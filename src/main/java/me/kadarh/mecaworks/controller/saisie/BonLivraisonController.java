package me.kadarh.mecaworks.controller.saisie;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonLivraison;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.EmployeService;
import me.kadarh.mecaworks.service.bons.BonLivraisonService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by salah on 4/15/18 5:36 PM.
 */
@Controller
@Slf4j
@RequestMapping("/saisi/livraisons")
public class BonLivraisonController {

	private final BonLivraisonService service;
	private final ChantierService chantierService;
	private final EmployeService employeService;

	public BonLivraisonController(BonLivraisonService service, ChantierService chantierService, EmployeService employeService) {
		this.service = service;
		this.chantierService = chantierService;
		this.employeService = employeService;
	}

	@GetMapping
	public String list(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
		model.addAttribute("page", service.bonList(pageable, search));
		model.addAttribute("search", search);
		return "saisi/livraisons/list";
	}

	@GetMapping("/add")
	public String addGet(Model model) {
		model.addAttribute("bonLivraison", new BonLivraison());
		model.addAttribute("chantiers", chantierService.getList());
		model.addAttribute("employes", employeService.getList());
		return "saisi/livraisons/add";
	}

	@PostMapping("/add")
	public String add(Model model, @Valid BonLivraison bonLivraison, BindingResult result) {
		boolean error = false;
		if (result.hasErrors() ||
			(error = bonLivraison.getChantierDepart().getId().equals(bonLivraison.getChantierArrivee().getId()))) {
			model.addAttribute("chantiers", chantierService.getList());
			model.addAttribute("employes", employeService.getList());
			model.addAttribute("errorChantier", error);
			return "saisi/livraisons/add";
		}
		service.add(bonLivraison);
		return "redirect:/saisi/livraisons";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "redirect:/saisi/livraisons";
	}
}
