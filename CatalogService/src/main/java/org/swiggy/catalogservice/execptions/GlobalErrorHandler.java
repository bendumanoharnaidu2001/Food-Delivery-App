package org.swiggy.catalogservice.execptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.swiggy.catalogservice.dto.response.ErrorResponse;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handelRestaurantNotFoundException(RestaurantNotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handelMenuItemNotFoundException(MenuItemNotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoMenuItemFoundException.class)
    public ResponseEntity<ErrorResponse> handelNoMenuItemFoundException(NoMenuItemFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
