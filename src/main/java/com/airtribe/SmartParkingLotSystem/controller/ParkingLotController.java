package com.airtribe.SmartParkingLotSystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.airtribe.SmartParkingLotSystem.dto.ParkingTicketDTO;
import com.airtribe.SmartParkingLotSystem.dto.ParkingSpotAvailabilityDTO;
import com.airtribe.SmartParkingLotSystem.entity.Vehicle;
import com.airtribe.SmartParkingLotSystem.exception.ParkingException;
import com.airtribe.SmartParkingLotSystem.service.ParkingLotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;


@RestController 
public class ParkingLotController {

    @Autowired 
    ParkingLotService parkingLotService;

     @GetMapping("/availability")
     public List<ParkingSpotAvailabilityDTO> getAvailability() {
         return parkingLotService.getAvailability();
     }
    
    @PostMapping("/checkin")
   public ParkingTicketDTO checkIn(@Valid @RequestBody Vehicle vehicle) throws ParkingException {
       ParkingTicketDTO ticket = parkingLotService.checkIn(vehicle);
       return ticket;
    }

    @PostMapping("/checkout")
    public ParkingTicketDTO checkOut(@RequestParam("ticketId") Long ticketId) throws ParkingException {
       ParkingTicketDTO ticket = parkingLotService.checkOut(ticketId);
       return ticket;
    }
}
