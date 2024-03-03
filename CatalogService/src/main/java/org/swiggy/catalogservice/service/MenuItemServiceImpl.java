package org.swiggy.catalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swiggy.catalogservice.dto.request.MenuItemIdsRequest;
import org.swiggy.catalogservice.dto.request.MenuItemListRequest;
import org.swiggy.catalogservice.dto.request.MenuItemRequest;
import org.swiggy.catalogservice.dto.response.MenuItemListResponse;
import org.swiggy.catalogservice.dto.response.MenuItemResponse;
import org.swiggy.catalogservice.execptions.RestaurantNotFoundException;
import org.swiggy.catalogservice.model.entite.MenuItem;
import org.swiggy.catalogservice.model.entite.Restaurant;
import org.swiggy.catalogservice.repository.MenuItemRepository;
import org.swiggy.catalogservice.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService{
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Override
    public MenuItemResponse addMenuItem(Long restaurantId, MenuItemListRequest request) {
        if(restaurantRepository.findById(restaurantId).isEmpty()) { return MenuItemResponse.builder().message("Restaurant not found").build();}
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<MenuItem> menuItemList = new ArrayList<>();
        for(MenuItem menuItem : request.getMenuItems()) {
            menuItem.setRestaurant(restaurant);
            menuItemList.add(menuItem);
        }
        menuItemRepository.saveAll(menuItemList);
        return MenuItemResponse.builder().message("Menu Item added successfully").build();
    }

    @Override
    public MenuItemResponse updateMenuItem(Long menuItemId, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow();
        menuItem.setPrice(request.getMenuItem().getPrice());
        menuItem.setName(request.getMenuItem().getName());
        menuItemRepository.save(menuItem);
        return MenuItemResponse.builder().message("Menu Item updated successfully").build();
    }

    @Override
    public MenuItemListResponse getMenuItems(MenuItemIdsRequest request) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        List<MenuItem> menuItemList = menuItemRepository.findAllById(request.getMenuItemIds());
        return MenuItemListResponse.builder().menuItems(menuItemList).restaurantLocation(restaurant.getLocation()).build();
    }
}
