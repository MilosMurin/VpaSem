package me.milos.murin.analyticsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsServiceApplication.class, args);
    }

    // Table -  Day : amount of rooms taken up

    // Table - Room : amount of days taken up
    // The same for room types

    // Table - Name : Value
    // 1 - Full - how many times was the pension full
    // 2 - Empty - how many times was the pension empty
    // 3 - Longest stay - what was the longest reservation
    // 4 - Cancelled stays - how many times was a reservation canceled

}
