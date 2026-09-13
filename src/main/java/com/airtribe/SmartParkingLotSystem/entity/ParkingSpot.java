package com.airtribe.SmartParkingLotSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.airtribe.SmartParkingLotSystem.generator.ParkingIdGenerator;

@Entity 
public class ParkingSpot {

    @Id
    @GenericGenerator(name = "parking_spot_id_generator", type = ParkingIdGenerator.class)
    @GeneratedValue(generator = "parking_spot_id_generator")
    String parkingId;
    @OneToOne
    Vehicle vehicle;
    @ManyToOne
    private ParkingFloor floorNumber;
    
    @Enumerated(EnumType.STRING)
    private SpotSize size;
    
    public ParkingSpot() {
    }

    public ParkingSpot(Vehicle vehicle, ParkingFloor floorNumber, SpotSize size) {
        this.vehicle = vehicle;
        this.floorNumber = floorNumber;
        this.size = size;
    }

    
    public String getParkingId() {
        return parkingId;
    }


    public ParkingFloor getFloorNumber() {
        return floorNumber;
    }

    public SpotSize getSize() {
        return size;
    }
    

    public synchronized boolean isAvailable() {
        return vehicle == null;
    }
    public synchronized boolean tryPark(Vehicle candidate) {
        if (vehicle != null || !size.canFit(candidate.getType())) {
            return false;
        }
        vehicle = candidate;
        return true;
    }
     
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


}
