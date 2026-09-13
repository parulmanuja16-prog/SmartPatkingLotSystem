package com.airtribe.SmartParkingLotSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airtribe.SmartParkingLotSystem.entity.ParkingTicket;

import java.util.*;

import jakarta.persistence.LockModeType;

/**
 * Repository for persisting and querying {@link ParkingTicket} entities.
 */
@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long>{

	/**
	 * Finds the active parking ticket for a vehicle by license plate, ignoring
	 * letter case.
	 *
	 * @param licensePlate license plate to search for
	 * @return the active ticket, or {@link Optional#empty()} when the vehicle is
	 *         not currently parked
	 */
	Optional<ParkingTicket> findByVehicle_LicensePlateIgnoreCaseAndExitTimeIsNull(String licensePlate);

	/**
	 * Finds a ticket by ID while acquiring a pessimistic write lock on its row.
	 *
	 * @param ticketId identifier of the ticket to find
	 * @return the matching ticket, or {@link Optional#empty()} when no ticket
	 *         exists with the specified ID
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select ticket from ParkingTicket ticket where ticket.ticketId = :ticketId")
	Optional<ParkingTicket> findByIdForUpdate(Long ticketId);

}
