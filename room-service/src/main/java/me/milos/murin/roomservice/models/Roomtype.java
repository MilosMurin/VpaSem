package me.milos.murin.roomservice.models;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roomtype")
@JsonFilter("myFilter")
public class Roomtype {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer bedAmount;

    private Integer extraBeds;

    private Integer price;

    private Integer size;

    private String info;
    private String name;

    public Roomtype() {
    }

    public Roomtype(Integer bedAmount, Integer extraBeds, Integer price, Integer size, String info, String name) {
        this.bedAmount = bedAmount;
        this.extraBeds = extraBeds;
        this.price = price;
        this.size = size;
        this.info = info;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBedAmount() {
        return bedAmount;
    }

    public Integer getExtraBeds() {
        return extraBeds;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getSize() {
        return size;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%d: %s, %d beds + %d extra, %dm2 for %d€", id, name, bedAmount, extraBeds, size, price);
    }
}
