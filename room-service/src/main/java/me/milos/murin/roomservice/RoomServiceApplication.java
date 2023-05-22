package me.milos.murin.roomservice;

import me.milos.murin.roomservice.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoomServiceApplication {


	private static final Logger log = LoggerFactory.getLogger(RoomServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RoomServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(RoomRepository repository) {
		return (args) -> {

//			// fetch all customers
//			log.info("Roomtypes found with findAll():");
//			log.info("-------------------------------");
//			for (Room roomtype : repository.findAll()) {
//				log.info(roomtype.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Optional<Room> roomtype = repository.findById(1);
//			if (roomtype.isPresent()) {
//				log.info("Customer found with findById(1):");
//				log.info("--------------------------------");
//				log.info(roomtype.get().toString());
//				log.info("");
//			}
		};
	}
}
