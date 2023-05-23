package me.milos.murin.roomservice.controllers;

import me.milos.murin.roomservice.models.Room;
import me.milos.murin.roomservice.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class RoomInfoController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/room/{number}")
    public ModelAndView roomInfo(Model model, @PathVariable String number) {
        try {
            Optional<Room> room = roomRepository.findById(Integer.parseInt(number));
            if (room.isPresent()) {
                model.addAttribute("room", room.get().getRoomType());
            } else {
                return new ModelAndView("redirect:http://localhost:8006/error");
            }
        } catch (NumberFormatException e) {
            return new ModelAndView("redirect:/");
        }
        model.addAttribute("num", number);
        loadToModel(model, "room", "Room n." + number);
        return new ModelAndView("roomInfo");
    }

    @GetMapping("/")
    public String roomList(Model model) {
        loadToModel(model, "rooms", "Rooms");
        ArrayList<Room> rooms = (ArrayList<Room>) roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        // TODO: List of all rooms with button to reserve
        model.addAttribute("number", "0");
        return "roomList";
    }

    @GetMapping("/error")
    public ModelAndView error(Model model) {
        return new ModelAndView("redirect:http://localhost:8006/error");
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
