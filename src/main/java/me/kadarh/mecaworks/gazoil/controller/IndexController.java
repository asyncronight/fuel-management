package me.kadarh.mecaworks.gazoil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kadarH
 */

@Controller
@RequestMapping("")
public class IndexController {

    @RequestMapping("")
    public String index() {
        return "home";
    }

    @RequestMapping("/admin")
    public String adminHome() {
        return "admin/add";
    }
}
