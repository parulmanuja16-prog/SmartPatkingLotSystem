package com.airtribe.SmartParkingLotSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airtribe.SmartParkingLotSystem.entity.ParkingFloor;
@Repository 
public interface ParkingFloorRepository extends JpaRepository<ParkingFloor, Integer>{

}
