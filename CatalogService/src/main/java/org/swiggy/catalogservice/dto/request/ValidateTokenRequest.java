package org.swiggy.catalogservice.dto.request;

import lombok.Data;
import org.swiggy.catalogservice.dto.enums.UserType;

@Data
public class ValidateTokenRequest {
    private String email;
    private String token;
    private UserType userType;
}
