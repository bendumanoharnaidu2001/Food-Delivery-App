package org.swiggy.orderservice.dto.request;

import lombok.Data;
import org.swiggy.orderservice.dto.enums.UserType;

@Data
public class ValidateTokenRequest {
    private String email;
    private String token;
    private UserType userType;
}
