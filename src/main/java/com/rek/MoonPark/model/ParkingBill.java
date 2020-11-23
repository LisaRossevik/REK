package com.rek.MoonPark.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ParkingBill {

    @Id
    @GeneratedValue
    private long id;
    private String zone;

    public ParkingBill() { }

    public ParkingBill(long id, String zone) {
        this.id = id;
        this.zone = zone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
