package me.kadarh.mecaworks.controller.user;

import me.kadarh.mecaworks.service.user.DashbordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

/**
 * Created on 5/16/18 9:58 PM
 *
 * @author salah3x
 */
@Controller
@RequestMapping("/user/gazoil")
public class GazoilController {

    private final DashbordService dashbordService;

    public GazoilController(DashbordService dashbordService) {
        this.dashbordService = dashbordService;
    }

    @GetMapping
    public String home(Model model) {
        LocalDate date = LocalDate.now();
        model.addAttribute("data", dashbordService.getDashbord(date.getMonthValue(), date.getYear()));
        return "user/gazoil/home";
    }
}
