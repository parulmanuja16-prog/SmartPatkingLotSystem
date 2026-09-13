package com.airtribe.SmartParkingLotSystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.airtribe.SmartParkingLotSystem.enums.VehicleType;

import jakarta.persistence.Column;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a vehicle registered with the parking lot.
 *
 * <p>Vehicles are considered equal when their license plates match without
 * regard to letter case.</p>
 */
@Entity 
public class Vehicle {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleID;
    @Column(nullable = false, unique = true)
    @NotNull 
    @NotEmpty
    @NotBlank 
    private String licensePlate;
    
    @NotNull
     @Enumerated(EnumType.STRING)
    private VehicleType type;

    /**
     * Creates an empty vehicle for JPA.
     */
    protected Vehicle() {
    }

    /**
     * Creates a vehicle with a license plate and vehicle type.
     *
     * @param licensePlate vehicle license plate
     * @param type vehicle category
     * @throws IllegalArgumentException if the license plate is blank or the
     *                                  vehicle type is {@code null}
     */
    public Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.isBlank() || type == null) {
            throw new IllegalArgumentException("License plate and vehicle type are required");
        }
        this.licensePlate = licensePlate;
        this.type = type;
    }

    /**
     * Returns the vehicle license plate.
     *
     * @return vehicle license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Returns the vehicle category.
     *
     * @return vehicle type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Compares vehicles by their license plates without regard to letter case.
     *
     * @param other object to compare with this vehicle
     * @return {@code true} when both objects are vehicles with matching license
     *         plates
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vehicle vehicle)) {
            return false;
        }
        return licensePlate.equalsIgnoreCase(vehicle.licensePlate);
    }

    /**
     * Returns a hash code derived from the normalized license plate.
     *
     * @return hash code for this vehicle
     */
    @Override
    public int hashCode() {
        return licensePlate.toUpperCase().hashCode();
    }


}
