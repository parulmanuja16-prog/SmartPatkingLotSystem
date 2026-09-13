package com.airtribe.SmartParkingLotSystem.exception;

/**
 * Checked exception for errors encountered during parking operations.
 */
public class ParkingException extends Exception{

    /**
     * Creates a parking exception with a descriptive message.
     *
     * @param message description of the parking error
     */
    public ParkingException(String message){
        super(message);
    }
}
