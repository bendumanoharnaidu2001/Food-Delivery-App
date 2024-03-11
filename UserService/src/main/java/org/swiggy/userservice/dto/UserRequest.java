package org.swiggy.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.enums.UserType;

@Data
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    private Location location;
    private UserType userType;
}
