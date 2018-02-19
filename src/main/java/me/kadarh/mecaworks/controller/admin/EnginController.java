package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.domain.others.Engin;
import me.kadarh.mecaworks.service.EnginService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by salah on 2/17/18 10:34 PM.
 */
@Controller
@RequestMapping("/admin/engins")
public class EnginController {

	private final EnginService enginService;

	public EnginController(EnginService enginService) {
		this.enginService = enginService;
	}

	@GetMapping("")
	public String list(Model model, Pageable pageable, @RequestParam(defaultValue = "") String search) {
		model.addAttribute("page", enginService.enginList(pageable, search));
		model.addAttribute("search", search);
		return "admin/engins/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("engin", new Engin());
		return "admin/engins/add";
	}

	@PostMapping("/add")
	public String addPost(@Valid Engin engin, BindingResult result) {
		if (result.hasErrors())
			return "admin/engins/add";
		enginService.add(engin);
		return "redirect:/admin/engins";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable Long id) {
		model.addAttribute("engin", enginService.get(id));
		model.addAttribute("edit", true);
		return "admin/engins/add";
	}

	@PostMapping("/{id}/edit")
	public String editPost(Model model, @Valid Engin engin, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("edit", true);
			return "admin/engins/add";
		}
		enginService.update(engin);
		return "redirect:/admin/engins";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		enginService.delete(id);
		return "redirect:/admin/engins";
	}
}
