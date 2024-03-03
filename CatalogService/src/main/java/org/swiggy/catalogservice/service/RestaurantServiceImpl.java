package org.swiggy.catalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.catalogservice.dto.request.RestaurantRequest;
import org.swiggy.catalogservice.dto.response.MenuItemListResponse;
import org.swiggy.catalogservice.dto.response.RestaurantResponse;
import org.swiggy.catalogservice.execptions.NoMenuItemFoundException;
import org.swiggy.catalogservice.execptions.RestaurantNotFoundException;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;
import org.swiggy.catalogservice.model.entite.Restaurant;
import org.swiggy.catalogservice.repository.MenuItemRepository;
import org.swiggy.catalogservice.repository.RestaurantRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;
    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        if(restaurantRepository.findByName(request.getName()).isPresent()) {
            return RestaurantResponse.builder().message("Restaurant already present").build();
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setLocation(request.getLocation());
        restaurant = restaurantRepository.save(restaurant);
        List<MenuItem> menuItems = new ArrayList<>();
        for (MenuItem menuItem : request.getMenuItems()) {
            menuItem.setRestaurant(restaurant);
            menuItems.add(menuItem);
        }
        menuItemRepository.saveAll(menuItems);
        return RestaurantResponse.builder().message("Restaurant added successfully").build();
    }

    @Override
    public MenuItemListResponse getListOfMenuItems(Long restaurantId) throws RestaurantNotFoundException, NoMenuItemFoundException {
        if(!isRestaurantPresent(restaurantId)) { throw new RestaurantNotFoundException("Restaurant not found");}
        if(menuItemRepository.findByRestaurantId(restaurantId).isEmpty()) { throw new NoMenuItemFoundException("No menu items found");} 
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        return MenuItemListResponse.builder().menuItems(menuItems).build();
    }

    @Override
    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request) throws RestaurantNotFoundException {
        Restaurant restaurant  = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        restaurant.setName(request.getName());
        restaurant.setLocation(request.getLocation());
        restaurantRepository.save(restaurant);
        return RestaurantResponse.builder().message("Restaurant updated successfully").build();
    }

    public boolean isRestaurantPresent(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.isPresent();
    }
}

