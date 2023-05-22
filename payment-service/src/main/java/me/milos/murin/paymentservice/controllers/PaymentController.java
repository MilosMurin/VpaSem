package me.milos.murin.paymentservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepostitory paymentRepostitory;


    @GetMapping("/payCreate/{amount}")
    public @ResponseBody String createPayment(@PathVariable String amount) {

        try {
            Integer i = Integer.parseInt(amount);
            Payment payment = new Payment(i);
            paymentRepostitory.save(payment);
            FilterProvider filters = new SimpleFilterProvider().addFilter(
                    "myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id"));

            return new ObjectMapper().writer(filters).writeValueAsString(payment);
        } catch (NumberFormatException e) {
            return "Not a number"; // TODO: redirect to error page
        } catch (JsonProcessingException e) {
            return "Error when JSON processing"; // TODO: redirect to error page
        }
    }

    @GetMapping("/pay/{id}")
    public String pay(Model model, @PathVariable String id) { // external redirect

        Optional<Payment> payment;
        try {
            payment = paymentRepostitory.findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        // TODO: Screen with card fill up and stuff
        if (payment.isPresent()) {

            model.addAttribute("amount", payment.get().getSum().toString() + "€");

            return "payment";
        }

        return "payment";// TODO: Redirect to home service
    }

    @GetMapping("/")
    public ModelAndView home() { // internal redirect

        // TODO: Redirect to home service

        String serviceUrl = "http://localhost:8003/room/212";
        return new ModelAndView("redirect:" + serviceUrl);
    }

}
