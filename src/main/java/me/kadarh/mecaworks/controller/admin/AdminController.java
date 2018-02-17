package me.kadarh.mecaworks.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by salah on 2/15/18 10:34 PM.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("")
	public String home() {
		return "admin/home";
	}
}
