package me.kadarh.mecaworks.controller.user.gazoil;

import me.kadarh.mecaworks.service.user.DashbordChantierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Created on 6/2/18 10:59 PM
 *
 * @author salah3x
 */

@Controller
@RequestMapping("/user/gazoil/chantiers")
public class DashbordChantierController {

	private final DashbordChantierService service;

	public DashbordChantierController(DashbordChantierService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public String getDashboardChantier(Model model, @PathVariable Long id,
	                                   @RequestParam(defaultValue = "") String date, Locale locale) {
		model.addAttribute("months", GazoilController.generate(locale));
		int year = date.isEmpty() ? LocalDate.now().getYear() : Integer.valueOf(date.split("-")[1]);
		int month = date.isEmpty() ? LocalDate.now().getMonthValue() : Integer.valueOf(date.split("-")[0]);
		model.addAttribute("now", Month.of(month).getDisplayName(TextStyle.FULL, locale) + " " + year);
		model.addAttribute("data", service.getDashbordChantier(id, month, year));
		return "user/gazoil/chantier/home";
	}
}
