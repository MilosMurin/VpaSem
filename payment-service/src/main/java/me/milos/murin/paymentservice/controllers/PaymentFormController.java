package me.milos.murin.paymentservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PaymentFormController {


    @GetMapping("/pay/{amount}")
    public String payAmount(Model model, @PathVariable String amount) {
        model.addAttribute("message", "Hello. You need to pay " + amount + "€!");
        return "payment"; // this will resolve to "hello.html" template
    }

    @GetMapping("/pay")
    public ModelAndView pay() { // external redirect
        String serviceUrl = "http://localhost:8003/room/212";
        return new ModelAndView("redirect:" + serviceUrl);
    }

    @GetMapping("/")
    public RedirectView home() { // internal redirect
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/pay/80");
        return redirectView;
    }

}
