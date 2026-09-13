package com.airtribe.SmartParkingLotSystem.dto;

import com.airtribe.SmartParkingLotSystem.entity.Vehicle;


/**
 * Data transfer object containing the details of a parking ticket.
 */
public class ParkingTicketDTO {
    private Long ticketId;
    private Vehicle vehicle;
    private String spotNumber;
    private String entryTime;
    private String exitTime;
    private Double fee;

    
    /**
     * Creates an empty ticket DTO.
     */
    public ParkingTicketDTO() {
    }

    /**
     * Creates a ticket DTO with the supplied details.
     *
     * @param ticketId ticket identifier
     * @param vehicle vehicle associated with the ticket
     * @param spotNumber assigned parking spot identifier
    * @param entryTime formatted vehicle entry time
    * @param exitTime formatted vehicle exit time, or {@code null} while active
     * @param fee parking fee, or {@code null} while active
     */
        public ParkingTicketDTO(Long ticketId, Vehicle vehicle, String spotNumber, String entryTime,
            String exitTime, Double fee) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spotNumber = spotNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }
    /**
     * @return ticket identifier
     */
    public Long getTicketId() {
        return ticketId;
    }

    /**
     * @param ticketId ticket identifier to assign
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    /**
     * @return vehicle associated with the ticket
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param vehicle vehicle to associate with the ticket
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    /**
     * @return assigned parking spot identifier
     */
    public String getSpotNumber() {
        return spotNumber;
    }

    /**
     * @param spotNumber parking spot identifier to assign
     */
    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }
    /**
     * @return vehicle entry time
     */
    public String getEntryTime() {
        return entryTime;
    }

    /**
    * @param entryTime formatted vehicle entry time to assign
     */
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }
    /**
     * @return vehicle exit time, or {@code null} while active
     */
    public String getExitTime() {
        return exitTime;
    }

    /**
    * @param exitTime formatted vehicle exit time to assign
     */
    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }
    /**
     * @return parking fee, or {@code null} while active
     */
    public Double getFee() {
        return fee;
    }

    /**
     * @param fee parking fee to assign
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }      
    
    


}
