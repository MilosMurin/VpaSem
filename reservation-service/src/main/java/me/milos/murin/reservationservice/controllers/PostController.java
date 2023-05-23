package me.milos.murin.reservationservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.milos.murin.reservationservice.models.PaymentId;
import me.milos.murin.reservationservice.models.Price;
import me.milos.murin.reservationservice.models.Reservation;
import me.milos.murin.reservationservice.models.RoomReservation;
import me.milos.murin.reservationservice.models.RoomResrvationId;
import me.milos.murin.reservationservice.repositories.ReservationRepository;
import me.milos.murin.reservationservice.repositories.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PostController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @PostMapping("/{id}")
    public String createReservation(@RequestParam String dateFrom, @RequestParam String dateTo,
            @RequestParam String name, @RequestParam String email, @RequestParam String phone,
            @PathVariable String id) {

        Integer rId = null;
        try {
            rId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return "redirect:http://localhost:8006/error";
        }

        // get the full price from room service
        String url = "http://localhost:8003/price/" + id;
        Mono<String> res = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        Integer days = null;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime date1 = LocalDate.parse(dateFrom, dtf).atStartOfDay();
            LocalDateTime date2 = LocalDate.parse(dateTo, dtf).atStartOfDay();
            days = Math.toIntExact(Duration.between(date1, date2).toDays());
        } catch (DateTimeException e) {
            return "redirect:http://localhost:8006/error";
        }

        Price p = null;
        try {
            p = objectMapper.readValue(res.block(), Price.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int price = p.getPrice() * days;

        // send price and recieve id
        res = webClientBuilder.build()
                .get()
                .uri("http://localhost:8001/payCreate/" + price)
                .retrieve()
                .bodyToMono(String.class);
        Long payId = null; // generate from payment service
        try {
            payId = objectMapper.readValue(res.block(), PaymentId.class).getId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Reservation reservation = new Reservation(name, email, phone, payId, false);
        reservationRepository.save(reservation);

        RoomReservation roomReservation = new RoomReservation(new RoomResrvationId(reservation, rId),
                Date.valueOf(dateFrom), Date.valueOf(dateTo));
        roomReservationRepository.save(roomReservation);

        // user selects date
        // user selects rooms (do in js and make a json list out of that)
        // user inputs personal info
        // user sends post request with all that info

        return "redirect:http://localhost:8001/pay/" + payId;
    }

}
