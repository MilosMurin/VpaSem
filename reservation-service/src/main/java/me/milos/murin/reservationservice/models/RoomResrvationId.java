package me.milos.murin.reservationservice.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoomResrvationId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private Integer roomId;

    public RoomResrvationId() {
    }

    public RoomResrvationId(Reservation reservation, Integer roomId) {
        this.reservation = reservation;
        this.roomId = roomId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof RoomResrvationId other) {
            return Objects.equals(reservation, other.reservation) && Objects.equals(roomId, other.roomId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, roomId);
    }
}
