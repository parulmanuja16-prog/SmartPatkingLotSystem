package com.airtribe.SmartParkingLotSystem.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity 
public class ParkingFloor {
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private  Integer number;
    
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "floorNumber", cascade = CascadeType.ALL)    
    private List<ParkingSpot> spots;

    public ParkingFloor(List<ParkingSpot> spots) {
        this.spots = spots;
    }
    public ParkingFloor() {
    }
    public int getNumber() {
        return number;
    }
    public synchronized List<ParkingSpot> getSpots() {
        return this.spots;
    }


    

}
