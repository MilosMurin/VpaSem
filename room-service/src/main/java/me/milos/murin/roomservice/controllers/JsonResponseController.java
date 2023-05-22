package me.milos.murin.roomservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import me.milos.murin.roomservice.RoomServiceApplication;
import me.milos.murin.roomservice.models.Room;
import me.milos.murin.roomservice.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class JsonResponseController {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceApplication.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(value = "/json/{number}", produces = "application/json")
    public @ResponseBody String roomJson(@PathVariable String number) {
        try {
            Room room = getRoom(Integer.parseInt(number));
            if (room != null) {
                return objectMapper.writeValueAsString(room);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return "Well nothing is here";
    }

    @GetMapping(value = "/price/{number}", produces = "application/json")
    public @ResponseBody String roomPrice(@PathVariable String number) {
        try {
            Room room = getRoom(Integer.parseInt(number));
            if (room != null) {
                FilterProvider filters
                        = new SimpleFilterProvider().addFilter(
                        "myFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("price"));
                return new ObjectMapper().writer(filters).writeValueAsString(room.getRoomType());
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return "Well nothing is here";
    }

    @GetMapping(value = "/all", produces = "application/json")
    public @ResponseBody String allRooms() {
        try {
            ArrayList<Room> rooms = (ArrayList<Room>) roomRepository.findAll();
            return objectMapper.writeValueAsString(rooms);
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return "Well nothing is here";
    }

    public Room getRoom(Integer number) {
        try {
            Optional<Room> room = roomRepository.findById(number);
            if (room.isPresent()) {
                return room.get();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
