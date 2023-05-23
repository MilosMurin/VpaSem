package me.milos.murin.roomservice.controllers;

import me.milos.murin.roomservice.models.Room;
import me.milos.murin.roomservice.models.Roomtype;
import me.milos.murin.roomservice.repositories.RoomRepository;
import me.milos.murin.roomservice.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

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
            return "Saved a new room";
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

}
