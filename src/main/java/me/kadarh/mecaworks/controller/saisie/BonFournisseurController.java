package me.kadarh.mecaworks.controller.saisie;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.domain.bons.BonFournisseur;
import me.kadarh.mecaworks.service.ChantierService;
import me.kadarh.mecaworks.service.FournisseurService;
import me.kadarh.mecaworks.service.bons.BonFournisseurService;
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
@RequestMapping("/saisi/fournisseurs")
public class BonFournisseurController {

	private final BonFournisseurService service;
	private final FournisseurService fournisseurService;
	private final ChantierService chantierService;

	public BonFournisseurController(BonFournisseurService service, FournisseurService fournisseurService, ChantierService chantierService) {
		this.service = service;
		this.fournisseurService = fournisseurService;
		this.chantierService = chantierService;
	}

	@GetMapping
	public String list(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
		model.addAttribute("page", service.bonList(pageable, search));
		model.addAttribute("search", search);
		return "saisi/fournisseurs/list";
	}

	@GetMapping("/add")
	public String addGet(Model model) {
		model.addAttribute("bonFournisseur", new BonFournisseur());
		model.addAttribute("fournisseurs", fournisseurService.getList());
		model.addAttribute("chantiers", chantierService.getList());
		return "saisi/fournisseurs/add";
	}

	@PostMapping("/add")
	public String add(Model model, @Valid BonFournisseur bonFournisseur, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("fournisseurs", fournisseurService.getList());
			model.addAttribute("chantiers", chantierService.getList());
			return "saisi/fournisseurs/add";
		}
		service.add(bonFournisseur);
		return "redirect:/saisi/fournisseurs";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "redirect:/saisi/fournisseurs";
	}
}
