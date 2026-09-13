package com.airtribe.SmartParkingLotSystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.airtribe.SmartParkingLotSystem.enums.VehicleType;

class ParkingLotServiceFeeTest {

    private static final LocalDateTime ENTRY_TIME = LocalDateTime.of(2026, 1, 1, 10, 0);

    @ParameterizedTest(name = "{0} minutes for {1} costs {2}")
    @MethodSource("feeCases")
    void calculateFeeBillsMinimumAndRoundedUpHours(long minutes, VehicleType vehicleType, double expectedFee)
            throws Exception {
        ParkingLotService service = new ParkingLotService(null, null, null, null);
        Method calculateFee = ParkingLotService.class.getDeclaredMethod(
                "calculateFee", VehicleType.class, LocalDateTime.class, LocalDateTime.class);
        calculateFee.setAccessible(true);

        LocalDateTime exitTime = ENTRY_TIME.plusMinutes(minutes);
        double actualFee = (double) calculateFee.invoke(service, vehicleType, ENTRY_TIME, exitTime);

        assertEquals(expectedFee, actualFee, 0.001);
    }

    private static Stream<Arguments> feeCases() {
        return Stream.of(
                Arguments.of(-1L, VehicleType.MOTORCYCLE, 15.0),
                Arguments.of(0L, VehicleType.MOTORCYCLE, 15.0),
                Arguments.of(1L, VehicleType.MOTORCYCLE, 15.0),
                Arguments.of(59L, VehicleType.MOTORCYCLE, 15.0),
                Arguments.of(60L, VehicleType.MOTORCYCLE, 15.0),
                Arguments.of(61L, VehicleType.MOTORCYCLE, 30.0),
                Arguments.of(119L, VehicleType.MOTORCYCLE, 30.0),
                Arguments.of(120L, VehicleType.MOTORCYCLE, 30.0),
                Arguments.of(121L, VehicleType.MOTORCYCLE, 45.0),
                Arguments.of(1440L, VehicleType.MOTORCYCLE, 360.0),
                Arguments.of(0L, VehicleType.CAR, 20.0),
                Arguments.of(61L, VehicleType.CAR, 40.0),
                Arguments.of(120L, VehicleType.CAR, 40.0),
                Arguments.of(121L, VehicleType.CAR, 60.0),
                Arguments.of(0L, VehicleType.BUS, 50.0),
                Arguments.of(61L, VehicleType.BUS, 100.0),
                Arguments.of(120L, VehicleType.BUS, 100.0),
                Arguments.of(121L, VehicleType.BUS, 150.0));
    }
}
