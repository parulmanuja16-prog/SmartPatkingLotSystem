package com.airtribe.SmartParkingLotSystem.repository;

import java.util.List;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;

@Repository 
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select spot from ParkingSpot spot order by spot.parkingId")
	List<ParkingSpot> findAllForUpdate();

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select spot from ParkingSpot spot where spot.parkingId = :parkingId")
	ParkingSpot findByIdForUpdate(String parkingId);
}
