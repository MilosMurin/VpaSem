package me.milos.murin.roomservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import me.milos.murin.roomservice.RoomServiceApplication;
import me.milos.murin.roomservice.models.Room;
import me.milos.murin.roomservice.models.Roomtype;
import me.milos.murin.roomservice.repositories.RoomRepository;
import me.milos.murin.roomservice.repositories.RoomTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class RoomInfoController {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceApplication.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(value = "/json/{number}", produces = "application/json")
    public @ResponseBody String roomJson(@PathVariable String number) {
        try {
            Room room = getRoom(number);
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
            Room room = getRoom(number);
            if (room != null) {
                FilterProvider filters
                        = new SimpleFilterProvider().addFilter(
                        "myFilter",
                        SimpleBeanPropertyFilter.filterOutAllExcept("price"));
                return new ObjectMapper().writer(filters).writeValueAsString(room.getRoomType());
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return "Well nothing is here";
    }


    @GetMapping("/room/{number}")
    public String roomInfo(Model model, @PathVariable String number) {
//        model.addAttribute("message", "Info about room number " + number);
        model.addAttribute("number", number);
        model.addAttribute("module", "home");
        return "roomInfo"; // this will resolve to "hello.html" template
    }

    @PostMapping(path = "/addRoom")
    public @ResponseBody String roomSave(@RequestParam String numberStr, @RequestParam String roomtypeStr) {
        try {
            Integer number = Integer.parseInt(numberStr);
            Integer roomtypeId = Integer.parseInt(roomtypeStr);
            Optional<Roomtype> roomtype = roomTypeRepository.findById(roomtypeId);
            if (roomtype.isPresent()) {
                Room room = new Room(number, roomtype.get());
                roomRepository.save(room);
            } else {
                return "No room type with the give id!";
            }
            return "Saved a new roomtype";
        } catch (NumberFormatException e) {
            return "Failed to create a room type from the parameters give.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping(path = "/addType")
    public @ResponseBody String roomTypeSave(@RequestParam String bedAmount, @RequestParam String extraBeds,
            @RequestParam String priceStr, @RequestParam String sizeStr, @RequestParam String info,
            @RequestParam String name) {
        try {
            Integer beds = Integer.parseInt(bedAmount);
            Integer extra = Integer.parseInt(extraBeds);
            Integer price = Integer.parseInt(priceStr);
            Integer size = Integer.parseInt(sizeStr);
            Roomtype roomtype = new Roomtype(beds, extra, price, size, info, name);
            roomTypeRepository.save(roomtype);
            return "Saved a new roomtype";
        } catch (NumberFormatException e) {
            return "Failed to create a room type from the parameters give.";
        }
    }

    private Room getRoom(String number) {
        try {
            Integer n = Integer.parseInt(number);
            Optional<Room> room = roomRepository.findById(n);
            if (room.isPresent()) {
                return room.get();
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
