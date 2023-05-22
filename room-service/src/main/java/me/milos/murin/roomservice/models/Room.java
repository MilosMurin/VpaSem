package me.milos.murin.roomservice.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Room {

    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roomtype_id", nullable = false)
    @JsonSerialize(using = RoomtypeSerializer.class)
    private Roomtype roomType;

    public Room() {
    }

    public Room(Integer id, Roomtype roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public Integer getId() {
        return id;
    }

    public Roomtype getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return String.format("Room no. %d: %d", id, roomType.getId());
    }
}
