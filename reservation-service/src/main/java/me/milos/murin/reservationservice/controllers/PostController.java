package me.milos.murin.reservationservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.milos.murin.reservationservice.models.Price;
import me.milos.murin.reservationservice.models.Reservation;
import me.milos.murin.reservationservice.models.RoomReservation;
import me.milos.murin.reservationservice.models.RoomResrvationId;
import me.milos.murin.reservationservice.repositories.ReservationRepository;
import me.milos.murin.reservationservice.repositories.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.ArrayList;

@Controller
public class PostController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @PostMapping("/create")
    public @ResponseBody String createReservation(@RequestParam String dateFrom, @RequestParam String dateTo,
            @RequestParam String name, @RequestParam String email, @RequestParam String phone,
            @RequestParam String rooms) {

        Integer price = 0;
        ArrayList<Integer> roomNumbers;
        try {
            roomNumbers = objectMapper.readValue(rooms, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (Integer i : roomNumbers) {
            // get the full price from room service
            String url = "http://localhost:8003/price/" + i.toString();
            Mono<String> res = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class);
            Price p = null;
            try {
                p = objectMapper.readValue(res.toString(), Price.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            price += p.getPrice();
        }

        // send price and recieve id
        Long payId = 0L; // generate from payment service
        // TODO: Retrieve payId

        Reservation reservation = new Reservation(name, email, phone, payId, false);
        reservationRepository.save(reservation);

        for (Integer i : roomNumbers) {
            RoomReservation roomReservation = new RoomReservation(new RoomResrvationId(reservation, i),
                    Date.valueOf(dateFrom), Date.valueOf(dateTo));
            roomReservationRepository.save(roomReservation);
        }
        // user selects date
        // user selects rooms (do in js and make a json list out of that)
        // user inputs personal info
        // user sneds post request with all that info

        return "";
    }

}
