package me.milos.murin.reservationservice.repositories;

import me.milos.murin.reservationservice.models.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {


}
