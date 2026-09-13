package com.airtribe.SmartParkingLotSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airtribe.SmartParkingLotSystem.entity.Vehicle;

import java.util.Optional;


/**
 * Repository for persisting and querying {@link Vehicle} entities.
 */
@Repository 
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	/**
	 * Finds a vehicle by its license plate without regard to letter case.
	 *
	 * @param licensePlate license plate to search for
	 * @return the matching vehicle, or {@link Optional#empty()} when no vehicle
	 *         matches
	 */
	Optional<Vehicle> findByLicensePlateIgnoreCase(String licensePlate);
}
