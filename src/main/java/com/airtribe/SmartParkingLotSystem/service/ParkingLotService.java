package com.airtribe.SmartParkingLotSystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airtribe.SmartParkingLotSystem.dto.ParkingTicketDTO;
import com.airtribe.SmartParkingLotSystem.dto.ParkingSpotAvailabilityDTO;
import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;
import com.airtribe.SmartParkingLotSystem.entity.ParkingTicket;
import com.airtribe.SmartParkingLotSystem.entity.Vehicle;
import com.airtribe.SmartParkingLotSystem.entity.VehicleType;
import com.airtribe.SmartParkingLotSystem.exception.ParkingException;
import com.airtribe.SmartParkingLotSystem.repository.ParkingFloorRepository;
import com.airtribe.SmartParkingLotSystem.repository.ParkingSpotRepository;
import com.airtribe.SmartParkingLotSystem.repository.ParkingTicketRepository;
import com.airtribe.SmartParkingLotSystem.repository.VehicleRepository;

@Service
public class ParkingLotService {

    ParkingFloorRepository parkingFloorRepository;
    ParkingTicketRepository parkingTicketRepository;
    ParkingSpotRepository spotRepository;
    VehicleRepository vehicleRepository;

    public ParkingLotService(ParkingFloorRepository parkingFloorRepository,
            ParkingTicketRepository parkingTicketRepository, ParkingSpotRepository spotRepository,
            VehicleRepository vehicleRepository) {
        this.parkingFloorRepository = parkingFloorRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.spotRepository = spotRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {       
        return vehicleRepository.save(vehicle);

    }

    @Transactional(readOnly = true)
    public List<ParkingSpotAvailabilityDTO> getAvailability() {
        return spotRepository.findAll().stream()
                .map(spot -> new ParkingSpotAvailabilityDTO(
                        spot.getFloorNumber().getNumber(),
                        spot.getParkingId(),
                        spot.getSize(),
                        spot.isAvailable()))
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public ParkingTicketDTO checkIn(Vehicle vehicleInput) throws ParkingException {
    List<ParkingSpot> spots = spotRepository.findAllForUpdate();

        if (parkingTicketRepository.findByVehicle_LicensePlateIgnoreCaseAndExitTimeIsNull(
                vehicleInput.getLicensePlate()).isPresent()) {
            throw new ParkingException("Vehicle is already parked.");
        }
        Vehicle vehicle = vehicleRepository.findByLicensePlateIgnoreCase(vehicleInput.getLicensePlate())
                .orElseGet(() -> saveVehicle(vehicleInput));
        ParkingSpot spot = allocate(spots, vehicle);
        if (spot == null)
            throw new ParkingException("No suitable parking spot is available");
        if (!spot.tryPark(vehicle)) {
            throw new ParkingException("Parking spot is no longer available");
        }
        spotRepository.save(spot);
        ParkingTicket ticket = new ParkingTicket(vehicle, spot, LocalDateTime.now(), null, null);
        parkingTicketRepository.save(ticket);
        ParkingTicketDTO ticketDTO = convertTicketEntityToDTO(ticket);
        return ticketDTO;
    }

    private ParkingTicketDTO convertTicketEntityToDTO(ParkingTicket ticket) {
      
        ParkingTicketDTO dto = new ParkingTicketDTO();
        dto.setTicketId(ticket.getTicketId());
        dto.setEntryTime(ticket.getEntryTime());
        dto.setExitTime(ticket.getExitTime());
        dto.setSpotNumber(ticket.getSpot().getParkingId());
        dto.setFee(ticket.getFee());
        dto.setVehicle(ticket.getVehicle());

      return dto;
    }

    public ParkingSpot allocate(List<ParkingSpot> spots, Vehicle vehicle) {
        return spots.stream()
                .filter(ParkingSpot::isAvailable)
                .filter(spot -> spot.getSize().canFit(vehicle.getType()))
                .sorted(Comparator.comparingInt(spot -> spot.getSize().ordinal()))
                .findFirst()
                .orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public ParkingTicketDTO checkOut(Long ticketId) throws ParkingException {
        Optional<ParkingTicket> ticketOptional = parkingTicketRepository.findByIdForUpdate(ticketId);
        if (ticketOptional.isEmpty()) {
            throw new ParkingException("Active ticket not found: " + ticketId);
        }
        ParkingTicket ticket = ticketOptional.get();
        if (ticket.getExitTime() != null) {
            throw new ParkingException("Ticket is already closed: " + ticketId);
        }
        ParkingSpot spot = spotRepository.findByIdForUpdate(ticket.getSpot().getParkingId());
        LocalDateTime exitTime = LocalDateTime.now();
        double fee = calculateFee(ticket.getVehicle().getType(), ticket.getEntryTime(), exitTime);
        spot.setVehicle(null);
        spotRepository.save(spot);

        ticket.close(exitTime, fee);
        ParkingTicket savedParkingTicket = parkingTicketRepository.save(ticket);
        return convertTicketEntityToDTO(savedParkingTicket);
       
    }

    private double calculateFee(VehicleType type, LocalDateTime entryTime, LocalDateTime exitTime) {     

        
        long billableHours = Math.max(1, (Duration.between(entryTime, exitTime).toMinutes() + 59) / 60);
        return BigDecimal.valueOf(billableHours * type.getHourlyRate())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    
    }

}
