package com.airtribe.SmartParkingLotSystem.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airtribe.SmartParkingLotSystem.dto.ParkingSpotAvailabilityDTO;
import com.airtribe.SmartParkingLotSystem.dto.ParkingTicketDTO;
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
/**
 * Provides vehicle registration, parking spot availability, check-in, and
 * check-out operations for the parking lot.
 */
public class ParkingLotService {

    private static final DateTimeFormatter TICKET_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

    ParkingFloorRepository parkingFloorRepository;
    ParkingTicketRepository parkingTicketRepository;
    ParkingSpotRepository spotRepository;
    VehicleRepository vehicleRepository;

    /**
     * Creates a parking lot service with the repositories used by its operations.
     *
     * @param parkingFloorRepository repository for parking floor data
     * @param parkingTicketRepository repository for parking ticket data
     * @param spotRepository repository for parking spot data
     * @param vehicleRepository repository for vehicle data
     */
    public ParkingLotService(ParkingFloorRepository parkingFloorRepository,
            ParkingTicketRepository parkingTicketRepository, ParkingSpotRepository spotRepository,
            VehicleRepository vehicleRepository) {
        this.parkingFloorRepository = parkingFloorRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.spotRepository = spotRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Saves a vehicle in the vehicle repository.
     *
     * @param vehicle vehicle to save
     * @return the saved vehicle
     */
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);

    }

    /**
     * Returns the current availability and details of every parking spot.
     *
     * @return availability details for all parking spots
     */
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

    /**
     * Checks a vehicle into the first available spot that can accommodate its
     * vehicle type and creates an active parking ticket.
     *
     * @param vehicleInput vehicle information supplied for check-in
     * @return the newly created parking ticket
     * @throws ParkingException if the vehicle is already parked or no suitable
     *                          parking spot is available
     */
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

    /**
     * Converts a parking ticket entity to its data transfer representation.
     *
     * @param ticket ticket entity to convert
     * @return DTO containing the ticket details
     */
    private ParkingTicketDTO convertTicketEntityToDTO(ParkingTicket ticket) {
      
        ParkingTicketDTO dto = new ParkingTicketDTO();
        dto.setTicketId(ticket.getTicketId());
        dto.setEntryTime(ticket.getEntryTime().format(TICKET_TIME_FORMATTER));
        dto.setExitTime(ticket.getExitTime() == null
            ? null
            : ticket.getExitTime().format(TICKET_TIME_FORMATTER));
        dto.setSpotNumber(ticket.getSpot().getParkingId());
        dto.setFee(ticket.getFee());
        dto.setVehicle(ticket.getVehicle());

      return dto;
    }

    /**
     * Selects the smallest available spot that can fit the vehicle.
     *
     * @param spots parking spots to consider
     * @param vehicle vehicle that needs a spot
     * @return the selected parking spot, or {@code null} when no spot fits
     */
    public ParkingSpot allocate(List<ParkingSpot> spots, Vehicle vehicle) {
        return spots.stream()
                .filter(ParkingSpot::isAvailable)
                .filter(spot -> spot.getSize().canFit(vehicle.getType()))
                .sorted(Comparator.comparingInt(spot -> spot.getSize().ordinal()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks a vehicle out, releases its parking spot, and calculates its fee.
     *
     * @param ticketId identifier of the active ticket to close
     * @return the closed parking ticket
     * @throws ParkingException if the ticket does not exist or is already closed
     */
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

    /**
     * Calculates the parking fee using whole billable hours, with a minimum of
     * one hour.
     *
     * @param type vehicle type used to determine the hourly rate
     * @param entryTime time at which the vehicle entered
     * @param exitTime time at which the vehicle exited
     * @return calculated parking fee rounded to two decimal places
     */
    private double calculateFee(VehicleType type, LocalDateTime entryTime, LocalDateTime exitTime) {

        
        long billableHours = Math.max(1, (Duration.between(entryTime, exitTime).toMinutes() + 59) / 60);
        return BigDecimal.valueOf(billableHours * type.getHourlyRate())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    
    }

}
