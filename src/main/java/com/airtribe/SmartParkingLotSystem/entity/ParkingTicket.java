package com.airtribe.SmartParkingLotSystem.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import com.airtribe.SmartParkingLotSystem.exception.ParkingException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class ParkingTicket {
    @Id   
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long ticketId;

    @OneToOne
    private Vehicle vehicle;

    @OneToOne(cascade = CascadeType.MERGE)
    private ParkingSpot spot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Double fee;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

    protected ParkingTicket() {
    }

    public ParkingTicket(Vehicle vehicle, ParkingSpot spot, LocalDateTime entryTime, LocalDateTime exitTime, Double fee) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Double getFee() {
        return fee;
    }
    public void close(LocalDateTime exitTime, double fee) throws ParkingException {
        if (this.exitTime != null) {
            throw new ParkingException("Ticket is already closed");
        }
        this.exitTime = exitTime;
        this.fee = fee;
    }
    
}
