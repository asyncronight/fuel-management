package me.kadarh.mecaworks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author kadarH
 */

@Controller
@RequestMapping("")
public class IndexController {

	@GetMapping("")
	public String index(@RequestParam(value = "22 u", defaultValue = "") String o) {
		if (!o.equals("")) {
			System.out.println("ok");
			return "";
		} else
		return "home";
	}
}
