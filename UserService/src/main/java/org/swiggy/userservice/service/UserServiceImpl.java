package org.swiggy.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.swiggy.userservice.dto.DeliveryExecutiveRequest;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.dto.UserResponse;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.entites.Users;
import org.swiggy.userservice.model.enums.Status;
import org.swiggy.userservice.model.enums.UserType;
import org.swiggy.userservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserResponse registerUser(UserRequest request) {
        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setPassword(request.getPassword());
        users.setContactNumber(request.getContactNumber());
        users.setLocation(request.getLocation());
        users.setUserType(request.getUserType());
        users.setCreatedDate(LocalDateTime.now());
        userRepository.save(users);
        return UserResponse.builder().message("User registered successfully").build();
    }

    @Override
    public Long assignPartner(Location location) {
        List<Users> deliveryPartners = userRepository.findByCity(location.getCity(), Status.ACTIVE, UserType.DELIVERY_EXECUTIVE);
        RestTemplate restTemplate = new RestTemplate();
        DeliveryExecutiveRequest request = DeliveryExecutiveRequest.builder().restaurantLocation(location).deliveryPartners(deliveryPartners).build();
        Long deliveryPartnerId = restTemplate.postForEntity("http://localhost:8083/deliveries", request, Long.class).getBody();
        Users deliveryPartner = userRepository.findById(deliveryPartnerId).orElse(null);
        deliveryPartner.setStatus(Status.OCCUPIED);
        userRepository.save(deliveryPartner);
        return deliveryPartnerId;
    }
}
