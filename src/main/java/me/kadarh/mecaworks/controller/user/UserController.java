package me.kadarh.mecaworks.controller.user;

import me.kadarh.mecaworks.domain.user.Dashbord;
import me.kadarh.mecaworks.service.user.DashbordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by salah on 2/15/18 10:24 PM.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DashbordService dashbordService;


	@GetMapping("")
	public String home() {
		return "user/home";
	}

    @GetMapping("/piw")
    public String homex() {

        Dashbord dashbord = dashbordService.getDashbord(4, 2018);
        return "user/home";
    }



}
