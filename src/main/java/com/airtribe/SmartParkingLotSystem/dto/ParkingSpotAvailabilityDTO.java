package com.airtribe.SmartParkingLotSystem.dto;

import com.airtribe.SmartParkingLotSystem.entity.SpotSize;

public class ParkingSpotAvailabilityDTO {

    private final Integer floorNumber;
    private final String spotNumber;
    private final SpotSize size;
    private final boolean available;

    public ParkingSpotAvailabilityDTO(Integer floorNumber, String spotNumber, SpotSize size, boolean available) {
        this.floorNumber = floorNumber;
        this.spotNumber = spotNumber;
        this.size = size;
        this.available = available;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public String getSpotNumber() {
        return spotNumber;
    }

    public SpotSize getSize() {
        return size;
    }

    public boolean isAvailable() {
        return available;
    }
}
