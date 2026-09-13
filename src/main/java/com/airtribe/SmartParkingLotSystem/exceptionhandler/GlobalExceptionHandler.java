package com.airtribe.SmartParkingLotSystem.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.airtribe.SmartParkingLotSystem.exception.ParkingException;

/**
 * Handles parking-related exceptions raised by the application's controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

   /**
    * Converts a {@link ParkingException} into an HTTP 400 response containing
    * the exception message.
    *
    * @param ex parking exception raised while processing a request
    * @return bad request response containing the exception message
    */
    @ExceptionHandler(ParkingException.class)
     public ResponseEntity handleParkingException(ParkingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
     }

}
