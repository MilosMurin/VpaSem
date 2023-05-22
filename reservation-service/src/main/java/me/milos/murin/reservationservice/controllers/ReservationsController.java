package me.milos.murin.reservationservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReservationsController {

    @GetMapping("/")
    public String showCalendar() {
        // TODO: Show calendar
        return "calendar";
    }

    @GetMapping("/{reservationId}")
    public String showCalendar(@PathVariable String reservationId) {
        // TODO: Show calendar with selected things from reservation
        return "calendar";
    }

}
