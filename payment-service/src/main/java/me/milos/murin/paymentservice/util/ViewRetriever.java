package me.milos.murin.paymentservice.util;

import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

public class ViewRetriever {

    private WebClient.Builder webClientBuilder;

    public ViewRetriever(Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void loadToModel(Model model, String title) {
        Mono<String> res = webClientBuilder.build()
                .get()
                .uri("http://localhost:8006/header/pay")
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
