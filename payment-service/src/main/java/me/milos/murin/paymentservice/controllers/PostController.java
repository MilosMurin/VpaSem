package me.milos.murin.paymentservice.controllers;

import me.milos.murin.paymentservice.models.PayerInfo;
import me.milos.murin.paymentservice.models.Payment;
import me.milos.murin.paymentservice.repositories.PayerInfoRepository;
import me.milos.murin.paymentservice.repositories.PaymentRepostitory;
import me.milos.murin.paymentservice.util.ViewRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PaymentRepostitory paymentRepostitory;

    @Autowired
    private PayerInfoRepository payerInfoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/pay/{id}")
    public ModelAndView payedFor(Model model, @PathVariable String id, @RequestParam String cardNumber,
            @RequestParam String expiration, @RequestParam String cvv) {

        Optional<Payment> payment;
        try {
            payment = paymentRepostitory.findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return new ModelAndView("redirect:http://localhost:8006/error");
        }

        if (payment.isPresent()) {

            payment.get().setPaid(true);
            paymentRepostitory.save(payment.get());
            payerInfoRepository.save(new PayerInfo(payment.get(), cardNumber, expiration, cvv));

            ViewRetriever vr = new ViewRetriever(webClientBuilder);
            vr.loadToModel(model, "Payment successfull");

            return new ModelAndView("paymentSuccessfull");
        }

        return new ModelAndView("redirect:http://localhost:8006/error");
    }

}
