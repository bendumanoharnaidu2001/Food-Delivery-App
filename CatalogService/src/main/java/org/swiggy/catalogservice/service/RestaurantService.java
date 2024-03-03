package org.swiggy.catalogservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.catalogservice.dto.request.RestaurantRequest;
import org.swiggy.catalogservice.dto.response.MenuItemListResponse;
import org.swiggy.catalogservice.dto.response.RestaurantResponse;
import org.swiggy.catalogservice.execptions.NoMenuItemFoundException;
import org.swiggy.catalogservice.execptions.RestaurantNotFoundException;
import org.swiggy.catalogservice.model.dto.Location;

@Service
public interface RestaurantService {
    RestaurantResponse createRestaurant(RestaurantRequest request);
    MenuItemListResponse getListOfMenuItems(Long restaurantId) throws RestaurantNotFoundException, NoMenuItemFoundException;
    RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request) throws RestaurantNotFoundException;

}
