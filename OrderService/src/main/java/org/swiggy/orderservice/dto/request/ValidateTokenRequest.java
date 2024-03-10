package org.swiggy.userservice.dto;

import lombok.Data;
import org.swiggy.userservice.model.enums.UserType;

@Data
public class ValidateTokenRequest {
    private String email;
    private String token;
    private UserType userType;
}
