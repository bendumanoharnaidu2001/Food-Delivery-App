package org.swiggy.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.dto.UserResponse;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.enums.UserType;
import org.swiggy.userservice.repository.UserRepository;
import org.swiggy.userservice.service.UserService;
import org.swiggy.userservice.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        reset(userService);
    }

    @Test
    public void testRegisterUser_Success() {
        Location location = new Location(12.9716, 77.5946, "Bangalore");
        UserRequest request = new UserRequest("username", "password", "contactNumber", "email", location, UserType.CUSTOMER);
        UserResponse expectedResponse = new UserResponse("User registered successfully");

        when(userService.registerUser(request)).thenReturn(expectedResponse);

        ResponseEntity<UserResponse> responseEntity = userController.registerUser(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(userService, times(1)).registerUser(request);
    }
    @Test
    void testToRegisterUser() {
        UserRequest userRequest = new UserRequest("Manohar","1234","manohar@gmail.com","9949798571",new Location(12.9716,77.5946,"Bangalore"), UserType.CUSTOMER);

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