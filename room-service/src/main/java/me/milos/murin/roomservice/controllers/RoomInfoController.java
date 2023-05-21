package me.milos.murin.roomservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomInfoController {

    @GetMapping("/room/{number}")
    public String payAmount(Model model, @PathVariable String number) {
        model.addAttribute("message", "Info about room number " + number);
        return "roomInfo"; // this will resolve to "hello.html" template
    }

}
