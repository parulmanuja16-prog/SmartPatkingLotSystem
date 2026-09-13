package com.airtribe.SmartParkingLotSystem.initializer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.airtribe.SmartParkingLotSystem.entity.ParkingFloor;
import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;
import com.airtribe.SmartParkingLotSystem.entity.SpotSize;
import com.airtribe.SmartParkingLotSystem.repository.ParkingFloorRepository;

@Component
/**
 * Initializes the default parking floors and parking spots when the
 * application starts.
 */
public class DataInitializer implements CommandLineRunner {

    private final ParkingFloorRepository parkingFloorRepository;

    /**
     * Creates an initializer backed by the parking floor repository.
     *
     * @param parkingFloorRepository repository used to persist parking floors
     *                               and their spots
     */
    public DataInitializer(ParkingFloorRepository parkingFloorRepository) {

        this.parkingFloorRepository = parkingFloorRepository;
    }

    /**
     * Creates the default parking lot data during application startup.
     *
     * @param args command-line arguments supplied by Spring Boot
     */
    @Override
    public void run(String... args) {
        initializeParkingFloors();
        initializeParkingSpots();
    }

    /**
     * Creates and persists the default set of four parking floors.
     */
    private void initializeParkingFloors() {

        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));
        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));
        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));
        parkingFloorRepository.save(new ParkingFloor(new ArrayList<>()));

    }

    /**
     * Adds two motorcycle, two compact, and two large spots to each floor.
     */
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

    /**
     * Adds a parking spot of the requested size to a floor.
     *
     * @param floor floor that receives the new spot
     * @param size size category of the new spot
     */
    private void addParkingSpot(ParkingFloor floor, SpotSize size) {
        floor.getSpots().add(new ParkingSpot(null, floor, size));
    }

}