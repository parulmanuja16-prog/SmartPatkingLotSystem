package com.airtribe.SmartParkingLotSystem.entity;


public enum VehicleType {
    MOTORCYCLE(15.0),
    CAR(20.0),
    BUS(50.0);

    private final double hourlyRate;

    VehicleType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
