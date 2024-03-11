package org.swiggy.userservice.service;

import fulfilmentservicegrpc.AssignDelivery;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.swiggy.userservice.dto.DeliveryExecutiveRequest;
import org.swiggy.userservice.dto.UserRequest;
import org.swiggy.userservice.dto.UserResponse;
import org.swiggy.userservice.dto.ValidateTokenRequest;
import org.swiggy.userservice.grpcClient.AssignDeliveryPartnerGrpcClient;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.entites.Token;
import org.swiggy.userservice.model.entites.Users;
import org.swiggy.userservice.model.enums.Status;
import org.swiggy.userservice.model.enums.UserType;
import org.swiggy.userservice.repository.TokenRepository;
import org.swiggy.userservice.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserResponse registerUser(UserRequest request) {
        Users users = new Users();
        users.setUsername(request.getUsername());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setContactNumber(request.getContactNumber());
        users.setEmail(request.getEmail());
        users.setLocation(request.getLocation());
        users.setUserType(request.getUserType());
        users.setCreatedDate(LocalDateTime.now());
        userRepository.save(users);
        return UserResponse.builder().message("User registered successfully").build();
    }

    @Override
    public Token login(UserRequest request) {
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            Token token = new Token();
            LocalDate today = LocalDate.now();
            LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

            // Convert LocalDate to Date
            Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

            token.setUser(user);
            token.setExpiryDate(expiryDate);
            token.setValue(RandomStringUtils.randomAlphanumeric(128));

            return tokenRepository.save(token);

        }
        return null;
    }

    @Override
    public Boolean verifyToken(ValidateTokenRequest request) {
        Token token = tokenRepository.findByValueAndUserEmailAndUserType(request.getToken(), request.getEmail(), request.getUserType()).orElse(null);
        if (token == null) {
            return false;
        }
        return token.getExpiryDate().after(new Date()) && !token.isDeleted();
    }


    @Override
    public Long assignPartner(Location location) {
        List<Users> deliveryPartners = userRepository.findByCity(location.getCity(), Status.ACTIVE, UserType.DELIVERY_EXECUTIVE);
        RestTemplate restTemplate = new RestTemplate();
        DeliveryExecutiveRequest request = DeliveryExecutiveRequest.builder().restaurantLocation(location).deliveryPartners(deliveryPartners).build();
        //Call Fulfilment service to get the nearest delivery partner
//       Long deliveryPartnerId = restTemplate.postForEntity("http://localhost:8083/deliveries", request, Long.class).getBody();
        AssignDelivery.DeliveryPartnerResponse deliveryPartnerId = AssignDeliveryPartnerGrpcClient.assignDeliveryPartner(location, deliveryPartners);
        Users deliveryPartner = userRepository.findById(deliveryPartnerId.getId()).orElse(null);
        deliveryPartner.setStatus(Status.OCCUPIED);
        userRepository.save(deliveryPartner);
        return deliveryPartnerId.getId();
    }

    @Override
    public Location getUserDetails(Long userId) {
        Users user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getLocation() : null;
    }
}
