package com.airtribe.SmartParkingLotSystem.entity;

public enum SpotSize {
    MOTORCYCLE(1),
    COMPACT(2),
    LARGE(3);

    private final int capacity;

    SpotSize(int capacity) {
        this.capacity = capacity;
    }

    public boolean canFit(VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE -> capacity >= MOTORCYCLE.capacity;
            case CAR -> capacity >= COMPACT.capacity;
            case BUS -> capacity >= LARGE.capacity;
            default ->false;
        };
    }
}

