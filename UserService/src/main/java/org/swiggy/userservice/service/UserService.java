package org.swiggy.userservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.dto.UserResponse;
import org.swiggy.userservice.model.dto.Location;

@Service
public interface UserService {
    UserResponse registerUser(UserRequest request);

    Long assignPartner(Location location);
}
