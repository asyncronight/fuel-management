package me.kadarh.mecaworks.gazoil.controller.saisie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by salah on 2/15/18 10:36 PM.
 */
@Controller
@RequestMapping("/saisi")
public class SaisiController {

	@GetMapping
	public String home() {
		return "saisi/home";
	}
}
