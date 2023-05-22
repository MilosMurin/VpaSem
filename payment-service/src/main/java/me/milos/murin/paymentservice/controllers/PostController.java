package me.milos.murin.paymentservice.controllers;

import me.milos.murin.paymentservice.models.PayerInfo;
import me.milos.murin.paymentservice.models.Payment;
import me.milos.murin.paymentservice.repositories.PayerInfoRepository;
import me.milos.murin.paymentservice.repositories.PaymentRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PaymentRepostitory paymentRepostitory;

    @Autowired
    private PayerInfoRepository payerInfoRepository;

    @PostMapping("/pay/{id}")
    public String payedFor(@PathVariable String id, @RequestParam String cardNumber,
            @RequestParam String expiration, @RequestParam String cvv) {

        Optional<Payment> payment;
        try {
            payment = paymentRepostitory.findById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e); // TODO: redirect to home
        }

        // TODO: Screen with card fill up and stuff
        if (payment.isPresent()) {

            payment.get().setPaid(true);
            paymentRepostitory.save(payment.get());
            payerInfoRepository.save(new PayerInfo(payment.get(), cardNumber, expiration, cvv));

            return "paymentSuccessfull";
        }

        return "payment"; // TODO: redirect to home
    }

}
