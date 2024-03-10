package org.swiggy.catalogservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserUtils {

    private boolean validateToken(String email, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/users/verify";
        ValidateTokenRequest request = new ValidateTokenRequest();
        request.setEmail(email);
        request.setToken(token);
        return restTemplate.postForEntity(url, request, Boolean.class).getBody();
    }
}
