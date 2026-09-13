package com.airtribe.SmartParkingLotSystem.repository;

import java.util.List;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;

/**
 * Repository for persisting and querying {@link ParkingSpot} entities.
 */
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, String>{

	/**
	 * Retrieves all parking spots ordered by parking ID while acquiring
	 * pessimistic write locks on the selected rows.
	 *
	 * @return all parking spots ordered by parking ID
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select spot from ParkingSpot spot order by spot.parkingId")
	List<ParkingSpot> findAllForUpdate();

	/**
	 * Retrieves a parking spot by ID while acquiring a pessimistic write lock on
	 * its row.
	 *
	 * @param parkingId identifier of the parking spot
	 * @return the matching parking spot, or {@code null} when no spot exists
	 *         with the specified ID
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select spot from ParkingSpot spot where spot.parkingId = :parkingId")
	ParkingSpot findByIdForUpdate(String parkingId);
}
