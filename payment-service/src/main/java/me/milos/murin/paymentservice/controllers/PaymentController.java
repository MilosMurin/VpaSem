package me.milos.murin.paymentservice.controllers;

import me.milos.murin.paymentservice.models.Payment;
import me.milos.murin.paymentservice.repositories.PaymentRepostitory;
import me.milos.murin.paymentservice.util.ViewRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Optional;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepostitory paymentRepostitory;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/payCreate/{amount}")
    public ModelAndView createPayment(@PathVariable String amount) {

        try {
            Integer i = Integer.parseInt(amount);
            Payment payment = new Payment(i);
            paymentRepostitory.save(payment);

            ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
            mav.addObject("id", payment.getId());

            return mav;
        } catch (NumberFormatException e) {
            return new ModelAndView("redirect:http://localhost:8006/error");
        }
    }

    @GetMapping("/pay/{id}")
    public ModelAndView pay(Model model, @PathVariable String id) { // external redirect

        Optional<Payment> payment;
        try {
            payment = paymentRepostitory.findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return new ModelAndView("redirect:http://localhost:8006/error");
        }

        if (payment.isPresent()) {
            if (!payment.get().getPaid()) {
                ViewRetriever vr = new ViewRetriever(webClientBuilder);
                vr.loadToModel(model, "Pay");
                model.addAttribute("amount", payment.get().getSum().toString() + "€");
                return new ModelAndView("payment");
            }
        }

        return new ModelAndView("redirect:http://localhost:8006/");
    }

    @GetMapping("/")
    public ModelAndView home() { // internal redirect
        return new ModelAndView("redirect:http://localhost:8006/");
    }

}
