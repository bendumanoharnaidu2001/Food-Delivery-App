package org.swiggy.userservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.dto.UserResponse;
import org.swiggy.userservice.dto.ValidateTokenRequest;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.entites.Token;

@Service
public interface UserService {
    UserResponse registerUser(UserRequest request);

    Token login(UserRequest request);

    Boolean verifyToken(ValidateTokenRequest request);

    Long assignPartner(Location location);

    Location getUserDetails(Long userId);
}
