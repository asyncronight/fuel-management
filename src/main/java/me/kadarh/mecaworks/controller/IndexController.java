package me.kadarh.mecaworks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kadarH
 */

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping("")
	public String index() {
		return "home";
	}

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
