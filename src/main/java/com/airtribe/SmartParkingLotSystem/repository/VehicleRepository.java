package com.airtribe.SmartParkingLotSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.airtribe.SmartParkingLotSystem.entity.Vehicle;

@Repository 
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	Optional<Vehicle> findByLicensePlateIgnoreCase(String licensePlate);
}
