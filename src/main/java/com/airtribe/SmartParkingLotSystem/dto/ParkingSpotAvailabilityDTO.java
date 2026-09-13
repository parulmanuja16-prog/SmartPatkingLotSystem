package com.airtribe.SmartParkingLotSystem.dto;

import com.airtribe.SmartParkingLotSystem.enums.SpotSize;

/**
 * Immutable data transfer object describing the availability of a parking
 * spot.
 */
public class ParkingSpotAvailabilityDTO {

    private final Integer floorNumber;
    private final String spotNumber;
    private final SpotSize size;
    private final boolean available;

    /**
     * Creates an availability DTO for a parking spot.
     *
     * @param floorNumber number of the floor containing the spot
     * @param spotNumber identifier of the parking spot
     * @param size size category of the spot
     * @param available whether the spot is currently available
     */
    public ParkingSpotAvailabilityDTO(Integer floorNumber, String spotNumber, SpotSize size, boolean available) {
        this.floorNumber = floorNumber;
        this.spotNumber = spotNumber;
        this.size = size;
        this.available = available;
    }

    /**
     * @return floor number containing the spot
     */
    public Integer getFloorNumber() {
        return floorNumber;
    }

    /**
     * @return parking spot identifier
     */
    public String getSpotNumber() {
        return spotNumber;
    }

    /**
     * @return spot size category
     */
    public SpotSize getSize() {
        return size;
    }

    /**
     * @return {@code true} when the spot is available
     */
    public boolean isAvailable() {
        return available;
    }
}
