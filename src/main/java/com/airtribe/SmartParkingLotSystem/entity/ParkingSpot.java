package com.airtribe.SmartParkingLotSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.airtribe.SmartParkingLotSystem.enums.SpotSize;
import com.airtribe.SmartParkingLotSystem.generator.ParkingIdGenerator;

/**
 * Represents a parking spot, including its floor, size, and currently parked
 * vehicle.
 */
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
    
    /**
     * Creates an empty parking spot for JPA.
     */
    public ParkingSpot() {
    }

    /**
     * Creates a parking spot with its initial occupancy and configuration.
     *
     * @param vehicle vehicle currently assigned to the spot, or {@code null}
     * @param floorNumber floor containing the spot
     * @param size size category of the spot
     */
    public ParkingSpot(Vehicle vehicle, ParkingFloor floorNumber, SpotSize size) {
        this.vehicle = vehicle;
        this.floorNumber = floorNumber;
        this.size = size;
    }

    
    /**
     * Returns the generated parking spot identifier.
     *
     * @return parking spot identifier
     */
    public String getParkingId() {
        return parkingId;
    }

    /**
     * Returns the floor containing this spot.
     *
     * @return containing parking floor
     */
    public ParkingFloor getFloorNumber() {
        return floorNumber;
    }

    /**
     * Returns the size category of this spot.
     *
     * @return spot size
     */
    public SpotSize getSize() {
        return size;
    }
    

    /**
     * Checks whether the spot is currently unoccupied.
     *
     * @return {@code true} when no vehicle is assigned to the spot
     */
    public synchronized boolean isAvailable() {
        return vehicle == null;
    }

    /**
     * Attempts to park a vehicle in this spot atomically.
     *
     * @param candidate vehicle to park
     * @return {@code true} when the vehicle was parked; {@code false} when the
     *         spot is occupied or too small for the vehicle
     */
    public synchronized boolean tryPark(Vehicle candidate) {
        if (vehicle != null || !size.canFit(candidate.getType())) {
            return false;
        }
        vehicle = candidate;
        return true;
    }
     
    /**
     * Returns the vehicle currently assigned to the spot.
     *
     * @return parked vehicle, or {@code null} when the spot is available
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Assigns or removes the vehicle occupying this spot.
     *
     * @param vehicle vehicle to assign, or {@code null} to make the spot
     *                available
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


}
