package com.airtribe.SmartParkingLotSystem;

import java.time.Clock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.airtribe.SmartParkingLotSystem.entity.ParkingSpot;
import com.airtribe.SmartParkingLotSystem.entity.ParkingTicket;
import com.airtribe.SmartParkingLotSystem.entity.SpotSize;
import com.airtribe.SmartParkingLotSystem.entity.Vehicle;
import com.airtribe.SmartParkingLotSystem.entity.VehicleType;
import com.airtribe.SmartParkingLotSystem.exception.ParkingException;
import com.airtribe.SmartParkingLotSystem.repository.VehicleRepository;
import com.airtribe.SmartParkingLotSystem.service.ParkingLotService;

@SpringBootApplication
public class SmartParkingLotSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SmartParkingLotSystemApplication.class, args);
	}

}
