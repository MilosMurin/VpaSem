package me.milos.murin.reservationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class ReservationsController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/")
    public String selectRooms() {
        return "redirect:http://localhost:8003/";
    }

    @GetMapping("/{roomId}")
    public String reserveSingle(Model model, @PathVariable String roomId) {
        loadToModel(model, "reservations", "Reserve room " + roomId);
        model.addAttribute("id", roomId);
        return "reserve";
    }

    public void loadToModel(Model model, String type, String title) {
        Mono<String> res = webClientBuilder.build()
                .get()
                .uri("http://localhost:8006/header/" + type)
                .retrieve()
                .bodyToMono(String.class);

        model.addAttribute("header", res.block());
        res = webClientBuilder.build()
                .get()
                .uri("http://localhost:8006/style/" + title)
                .retrieve()
                .bodyToMono(String.class);
        model.addAttribute("style", res.block());
    }

}
