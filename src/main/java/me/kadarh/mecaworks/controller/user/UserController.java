package me.kadarh.mecaworks.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by salah on 2/15/18 10:24 PM.
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@GetMapping("")
	public String home() {
		return "user/home";
	}
}
