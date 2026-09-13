package com.airtribe.SmartParkingLotSystem.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.airtribe.SmartParkingLotSystem.exception.ParkingException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParkingException.class)
     public ResponseEntity handleParkingException(ParkingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
     }

}
