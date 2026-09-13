package com.airtribe.SmartParkingLotSystem.initializer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.airtribe.SmartParkingLotSystem.entity.ParkingFloor;
import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;
import com.airtribe.SmartParkingLotSystem.entity.SpotSize;
import com.airtribe.SmartParkingLotSystem.entity.VehicleType;
import com.airtribe.SmartParkingLotSystem.generator.ParkingIdGenerator;
import com.airtribe.SmartParkingLotSystem.repository.ParkingFloorRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final String MOTORCYCLE = "MOTORCYCLE";

    private final ParkingFloorRepository parkingFloorRepository;

    public DataInitializer(ParkingFloorRepository parkingFloorRepository) {

        this.parkingFloorRepository = parkingFloorRepository;
    }

    @Override
    public void run(String... args) {
        initializeParkingFloors();
        initializeParkingSpots();
    }

    private void initializeParkingFloors() {

        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));
        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));
        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));
        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));

    }

    private void initializeParkingSpots() {
        SpotSize motorcycleSize = SpotSize.MOTORCYCLE;
        SpotSize compactSize = SpotSize.COMPACT;
        SpotSize largeSize = SpotSize.LARGE;

        List<ParkingFloor> parkingFloors = parkingFloorRepository.findAll();
        for (ParkingFloor floor : parkingFloors) {
            addParkingSpot(floor, motorcycleSize);
            addParkingSpot(floor, motorcycleSize);
            addParkingSpot(floor, compactSize);
            addParkingSpot(floor, compactSize);
            addParkingSpot(floor, largeSize);
            addParkingSpot(floor, largeSize);
            parkingFloorRepository.save(floor);
        }
    }

    private void addParkingSpot(ParkingFloor floor, SpotSize size) {
        floor.getSpots().add(new ParkingSpot(null, floor, size));
    }

}