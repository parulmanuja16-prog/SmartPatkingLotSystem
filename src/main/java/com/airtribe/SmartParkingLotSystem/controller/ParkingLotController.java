package com.airtribe.SmartParkingLotSystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.airtribe.SmartParkingLotSystem.dto.ParkingSpotAvailabilityDTO;
import com.airtribe.SmartParkingLotSystem.dto.ParkingTicketDTO;
import com.airtribe.SmartParkingLotSystem.entity.Vehicle;
import com.airtribe.SmartParkingLotSystem.exception.ParkingException;
import com.airtribe.SmartParkingLotSystem.service.ParkingLotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;


/**
 * Exposes REST endpoints for parking lot availability and vehicle parking
 * operations.
 */
@RestController 
public class ParkingLotController {

    @Autowired 
    ParkingLotService parkingLotService;

    /**
     * Returns the current availability of every parking spot.
     *
     * @return availability details for all parking spots
     */
    @GetMapping("/availability")
     public List<ParkingSpotAvailabilityDTO> getAvailability() {
         return parkingLotService.getAvailability();
     }
    
    /**
     * Checks a vehicle into the parking lot.
     *
     * @param vehicle validated vehicle details from the request body
     * @return the newly created parking ticket
     * @throws ParkingException if the vehicle cannot be parked
     */
    @PostMapping("/checkin")
   public ParkingTicketDTO checkIn(@Valid @RequestBody Vehicle vehicle) throws ParkingException {
       ParkingTicketDTO ticket = parkingLotService.checkIn(vehicle);
       return ticket;
    }

    /**
     * Checks a vehicle out of the parking lot using its ticket ID.
     *
     * @param ticketId active ticket identifier supplied as a request parameter
     * @return the closed parking ticket
     * @throws ParkingException if the ticket cannot be found or is already
     *                          closed
     */
    @PostMapping("/checkout")
    public ParkingTicketDTO checkOut(@RequestParam("ticketId") Long ticketId) throws ParkingException {
       ParkingTicketDTO ticket = parkingLotService.checkOut(ticketId);
       return ticket;
    }
}
