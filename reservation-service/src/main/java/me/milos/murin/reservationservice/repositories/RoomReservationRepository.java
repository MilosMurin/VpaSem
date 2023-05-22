package me.milos.murin.reservationservice.repositories;

import me.milos.murin.reservationservice.models.RoomReservation;
import me.milos.murin.reservationservice.models.RoomResrvationId;
import org.springframework.data.repository.CrudRepository;

public interface RoomReservationRepository extends CrudRepository<RoomReservation, RoomResrvationId> {


}
