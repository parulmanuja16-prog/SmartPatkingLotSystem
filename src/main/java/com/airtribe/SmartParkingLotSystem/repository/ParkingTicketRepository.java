package com.airtribe.SmartParkingLotSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import jakarta.persistence.LockModeType;
import com.airtribe.SmartParkingLotSystem.entity.ParkingTicket;

@Repository 
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long>{

	Optional<ParkingTicket> findByVehicle_LicensePlateIgnoreCaseAndExitTimeIsNull(String licensePlate);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select ticket from ParkingTicket ticket where ticket.ticketId = :ticketId")
	Optional<ParkingTicket> findByIdForUpdate(Long ticketId);

}
