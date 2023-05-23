package me.milos.murin.paymentservice.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import me.milos.murin.paymentservice.models.Payment;
import me.milos.murin.paymentservice.repositories.PaymentRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import reactor.core.publisher.Mono;

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
            FilterProvider filters = new SimpleFilterProvider().addFilter(
                    "myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"));

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

        // TODO: Screen with card fill up and stuff
        if (payment.isPresent()) {
            if (!payment.get().getPaid()) {
                Mono<String> res = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8006/header/pay")
                        .retrieve()
                        .bodyToMono(String.class);

                model.addAttribute("amount", payment.get().getSum().toString() + "€");
                model.addAttribute("header", res.block());
                res = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8006/style/Pay")
                        .retrieve()
                        .bodyToMono(String.class);
                model.addAttribute("style", res.block());

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
