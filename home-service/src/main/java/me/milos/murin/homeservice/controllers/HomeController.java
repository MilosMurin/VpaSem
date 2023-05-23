package me.milos.murin.homeservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("active", "index");
        return "index";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("active", "contact");
        return "contact";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("active", "error");
        return "error";
    }

    @GetMapping("/header/{selected}")
    public String header(Model model, @PathVariable String selected) {
        model.addAttribute("active", selected);
        return "fragments/header";
    }

    @GetMapping("/style/{title}")
    public String style(Model model, @PathVariable String title) {
        model.addAttribute("title", title);
        return "fragments/style";
    }

}
