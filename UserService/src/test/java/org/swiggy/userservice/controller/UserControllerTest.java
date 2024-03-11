package org.swiggy.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.enums.UserType;
import org.swiggy.userservice.repository.UserRepository;
import org.swiggy.userservice.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        reset(userService);
    }
    @Test
    void testToRegisterUser() {
        UserRequest userRequest = new UserRequest("Manohar","1234","manohar@gmail.com","9949798571",null, UserType.CUSTOMER);
//        userRepository.save(userRequest);
    }

    @Test
    void login() {
    }

    @Test
    void verifyToken() {
    }

    @Test
    void assignDeliveryPartner() {
    }

    @Test
    void getUserDetails() {
    }
}