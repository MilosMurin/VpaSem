package me.milos.murin.reservationservice.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.sql.Date;

@Entity
public class RoomReservation {

    @EmbeddedId
    private RoomResrvationId id;

    private Date dateFrom;

    private Date dateTo;

    public RoomReservation() {
    }

    public RoomReservation(RoomResrvationId id, Date dateFrom, Date dateTo) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Reservation getReservation() {
        return id.getReservation();
    }

    public void setReservation(Reservation reservation) {
        this.id.setReservation(reservation);
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getRoomId() {
        return id.getRoomId();
    }

    public void setRoomId(Integer roomId) {
        this.id.setRoomId(roomId);
    }

    public RoomResrvationId getId() {
        return id;
    }

    public void setId(RoomResrvationId id) {
        this.id = id;
    }
}
