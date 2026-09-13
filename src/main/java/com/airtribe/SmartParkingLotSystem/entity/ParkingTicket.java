package com.airtribe.SmartParkingLotSystem.entity;

import java.time.LocalDateTime;

import com.airtribe.SmartParkingLotSystem.exception.ParkingException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


/**
 * Represents a vehicle's parking session, including its assigned spot,
 * entry and exit times, and calculated fee.
 */
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

    /**
     * Creates an empty ticket for JPA.
     */
    protected ParkingTicket() {
    }

    /**
     * Creates a parking ticket with its session details.
     *
     * @param vehicle vehicle associated with the ticket
     * @param spot parking spot assigned to the vehicle
     * @param entryTime time at which the vehicle entered
     * @param exitTime time at which the vehicle exited, or {@code null} for an
     *                 active ticket
     * @param fee calculated parking fee, or {@code null} for an active ticket
     */
    public ParkingTicket(Vehicle vehicle, ParkingSpot spot, LocalDateTime entryTime, LocalDateTime exitTime, Double fee) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
    }

    /**
     * Returns the ticket identifier.
     *
     * @return ticket identifier
     */
    public Long getTicketId() {
        return ticketId;
    }

    /**
     * Returns the vehicle associated with the ticket.
     *
     * @return ticket vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Returns the parking spot assigned to the ticket.
     *
     * @return assigned parking spot
     */
    public ParkingSpot getSpot() {
        return spot;
    }

    /**
     * Returns the vehicle entry time.
     *
     * @return entry time
     */
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    /**
     * Returns the vehicle exit time.
     *
     * @return exit time, or {@code null} while the ticket is active
     */
    public LocalDateTime getExitTime() {
        return exitTime;
    }

    /**
     * Returns the parking fee.
     *
     * @return parking fee, or {@code null} while the ticket is active
     */
    public Double getFee() {
        return fee;
    }

    /**
     * Closes the ticket and records its exit time and fee.
     *
     * @param exitTime time at which the vehicle exited
     * @param fee calculated parking fee
     * @throws ParkingException if the ticket has already been closed
     */
    public void close(LocalDateTime exitTime, double fee) throws ParkingException {
        if (this.exitTime != null) {
            throw new ParkingException("Ticket is already closed");
        }
        this.exitTime = exitTime;
        this.fee = fee;
    }
    
}
