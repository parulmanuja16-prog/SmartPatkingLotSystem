package com.airtribe.SmartParkingLotSystem.enums;

/**
 * Parking spot size categories and their relative capacities.
 */
public enum SpotSize {
    /** Smallest spot size, suitable for motorcycles. */
    MOTORCYCLE(1),
    /** Compact spot size, suitable for cars and motorcycles. */
    COMPACT(2),
    /** Largest spot size, suitable for buses, cars, and motorcycles. */
    LARGE(3);

    private final int capacity;

    /**
     * Creates a spot size with the specified relative capacity.
     *
     * @param capacity relative capacity of the spot size
     */
    SpotSize(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Determines whether this spot size can accommodate a vehicle type.
     *
     * @param vehicleType type of vehicle to fit
     * @return {@code true} when this spot is large enough for the vehicle type;
     *         otherwise {@code false}
     */
    public boolean canFit(VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE -> capacity >= MOTORCYCLE.capacity;
            case CAR -> capacity >= COMPACT.capacity;
            case BUS -> capacity >= LARGE.capacity;
            default ->false;
        };
    }
}

