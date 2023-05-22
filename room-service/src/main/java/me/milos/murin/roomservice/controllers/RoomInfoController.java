package me.milos.murin.roomservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomInfoController {

    @GetMapping("/room/{number}")
    public String roomInfo(Model model, @PathVariable String number) {
//        model.addAttribute("message", "Info about room number " + number);
        model.addAttribute("number", number);
        model.addAttribute("module", "home");
        return "roomInfo";
    }

    @GetMapping("/")
    public String roomList(Model model) {
        // TODO: List of all rooms with button to reserve
        model.addAttribute("number", 0);
        model.addAttribute("module", "home");
        return "roomInfo";
    }

}
