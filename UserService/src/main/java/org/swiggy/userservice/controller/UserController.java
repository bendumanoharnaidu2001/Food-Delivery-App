package org.swiggy.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.dto.UserResponse;
import org.swiggy.userservice.dto.ValidateTokenRequest;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.entites.Token;
import org.swiggy.userservice.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {

        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyToken(@RequestBody ValidateTokenRequest request) {
        return new ResponseEntity<>(userService.verifyToken(request), HttpStatus.OK);
    }

    @PostMapping("/assign-delivery-partner")
    public ResponseEntity<Long> assignDeliveryPartner(@RequestBody Location location) {
        return new ResponseEntity<>(userService.assignPartner(location), HttpStatus.OK);
    }

    @GetMapping("/userDetails/{userId}")
    public ResponseEntity<Location> getUserDetails(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.OK);
    }
}
