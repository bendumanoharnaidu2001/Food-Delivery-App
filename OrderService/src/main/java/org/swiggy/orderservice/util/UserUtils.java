package org.swiggy.orderservice.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.swiggy.orderservice.dto.enums.UserType;
import org.swiggy.orderservice.dto.request.ValidateTokenRequest;

@Component
public class UserUtils {

    public boolean validateToken(String email, String token, UserType userType) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/users/verify";
        ValidateTokenRequest request = new ValidateTokenRequest();
        request.setEmail(email);
        request.setToken(token);
        request.setUserType(userType);
        return Boolean.TRUE.equals(restTemplate.postForEntity(url, request, Boolean.class).getBody());

    }
}
