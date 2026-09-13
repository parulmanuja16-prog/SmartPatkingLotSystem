package com.airtribe.SmartParkingLotSystem.dto;

import java.time.LocalDateTime;
import com.airtribe.SmartParkingLotSystem.entity.Vehicle;


public class ParkingTicketDTO {
    private Long ticketId;
    private Vehicle vehicle;
    private String spotNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Double fee;

    
    public ParkingTicketDTO() {
    }
    public ParkingTicketDTO(Long ticketId, Vehicle vehicle, String spotNumber, LocalDateTime entryTime,
            LocalDateTime exitTime, Double fee) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spotNumber = spotNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public String getSpotNumber() {
        return spotNumber;
    }
    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
    public LocalDateTime getExitTime() {
        return exitTime;
    }
    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
    public Double getFee() {
        return fee;
    }
    public void setFee(Double fee) {
        this.fee = fee;
    }      
    
    


}
