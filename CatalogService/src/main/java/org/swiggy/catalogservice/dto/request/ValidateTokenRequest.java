package org.swiggy.userservice.dto;

import lombok.Data;

@Data
public class ValidateTokenRequest {
    private String email;
    private String token;
}
