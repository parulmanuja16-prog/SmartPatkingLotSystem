package com.airtribe.SmartParkingLotSystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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

    protected Vehicle() {
    }

    public Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.isBlank() || type == null) {
            throw new IllegalArgumentException("License plate and vehicle type are required");
        }
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vehicle vehicle)) {
            return false;
        }
        return licensePlate.equalsIgnoreCase(vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return licensePlate.toUpperCase().hashCode();
    }


}
