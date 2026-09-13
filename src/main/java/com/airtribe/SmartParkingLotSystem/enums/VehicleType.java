package com.airtribe.SmartParkingLotSystem.enums;


/**
 * Vehicle categories supported by the parking lot and their hourly rates.
 */
public enum VehicleType {
    /** Motorcycle parking rate: 15.0 per hour. */
    MOTORCYCLE(15.0),
    /** Car parking rate: 20.0 per hour. */
    CAR(20.0),
    /** Bus parking rate: 50.0 per hour. */
    BUS(50.0);

    private final double hourlyRate;

    /**
     * Creates a vehicle type with its hourly parking rate.
     *
     * @param hourlyRate parking charge per hour
     */
    VehicleType(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hourly parking rate for this vehicle type.
     *
     * @return hourly parking rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }
}
