package com.airtribe.SmartParkingLotSystem.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


/**
 * Represents a parking floor and the spots assigned to it.
 */
@Entity 
public class ParkingFloor {
    @Id 
    @GeneratedValue (strategy = GenerationType.AUTO)
    private  Integer number;
    
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "floorNumber", cascade = CascadeType.ALL)    
    private List<ParkingSpot> spots;

    /**
     * Creates a parking floor with its initial spot collection.
     *
     * @param spots parking spots assigned to the floor
     */
    public ParkingFloor(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    /**
     * Creates an empty parking floor for JPA.
     */
    public ParkingFloor() {
    }

    /**
     * Returns the generated floor number.
     *
     * @return floor number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the spots assigned to this floor.
     *
     * @return floor parking spots
     */
    public synchronized List<ParkingSpot> getSpots() {
        return this.spots;
    }


    

}
