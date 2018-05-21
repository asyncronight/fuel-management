package me.kadarh.mecaworks.controller.user;

import me.kadarh.mecaworks.service.user.DashbordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

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
    public String home(Model model, @RequestParam(defaultValue = "") String date, Locale locale) {
	    model.addAttribute("months", generate(locale));
	    int year = date.isEmpty() ? LocalDate.now().getYear() : Integer.valueOf(date.split("-")[1]);
	    int month = date.isEmpty() ? LocalDate.now().getMonthValue() : Integer.valueOf(date.split("-")[0]);
	    model.addAttribute("now", Month.of(month).getDisplayName(TextStyle.FULL, locale) + " / " + year);
	    model.addAttribute("data", dashbordService.getDashbord(month, year));
        return "user/gazoil/home";
    }

	private Map<String, String> generate(Locale locale) {
		int month = LocalDate.now().getMonthValue();
		int year = LocalDate.now().getYear();

		Map<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < 12; i++) {
			if (month == 0) {
				month = 12;
				year--;
			}
			map.put(month + "-" + year, Month.of(month).getDisplayName(TextStyle.FULL, locale) + " / " + year);
			month--;
		}
		return map;
	}
}
