package org.swiggy.catalogservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.swiggy.catalogservice.dto.enums.UserType;
import org.swiggy.catalogservice.dto.request.ValidateTokenRequest;

@Component
public class UserUtils {

    public boolean validateToken(String email, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/users/verify";
        ValidateTokenRequest request = new ValidateTokenRequest();
        request.setEmail(email);
        request.setToken(token);
        request.setUserType(UserType.ADMIN);
        return Boolean.TRUE.equals(restTemplate.postForEntity(url, request, Boolean.class).getBody());

    }
}
