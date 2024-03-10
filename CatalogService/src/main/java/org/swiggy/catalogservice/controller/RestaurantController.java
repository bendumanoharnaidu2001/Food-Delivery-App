package org.swiggy.catalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.swiggy.catalogservice.dto.request.RestaurantRequest;
import org.swiggy.catalogservice.dto.response.MenuItemListResponse;
import org.swiggy.catalogservice.dto.response.RestaurantResponse;
import org.swiggy.catalogservice.execptions.NoMenuItemFoundException;
import org.swiggy.catalogservice.execptions.RestaurantNotFoundException;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.service.RestaurantService;
import org.swiggy.catalogservice.util.UserUtils;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserUtils userUtils;
    @PostMapping("/register")
    public ResponseEntity<RestaurantResponse> addRestaurant(@RequestBody RestaurantRequest request) {
        if(!userUtils.validateToken(request.getEmail(), request.getToken())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        RestaurantResponse response = restaurantService.createRestaurant(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable("restaurantId") Long restaurantId, @RequestBody RestaurantRequest request) throws RestaurantNotFoundException {
        if(!userUtils.validateToken(request.getEmail(), request.getToken())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        RestaurantResponse response = restaurantService.updateRestaurant(restaurantId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{restaurantId}")
    public ResponseEntity<MenuItemListResponse> getListOfMenuItems(@PathVariable("restaurantId") Long restaurantId) throws RestaurantNotFoundException, NoMenuItemFoundException {
        MenuItemListResponse response = restaurantService.getListOfMenuItems(restaurantId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
