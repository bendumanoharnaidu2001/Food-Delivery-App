package org.swiggy.catalogservice.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.swiggy.catalogservice.dto.request.MenuItemListRequest;
import org.swiggy.catalogservice.dto.request.MenuItemRequest;
import org.swiggy.catalogservice.dto.response.MenuItemResponse;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;
import org.swiggy.catalogservice.model.entite.Restaurant;
import org.swiggy.catalogservice.repository.MenuItemRepository;
import org.swiggy.catalogservice.repository.RestaurantRepository;
import org.swiggy.catalogservice.service.MenuItemService;
import org.swiggy.catalogservice.util.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class MenuItemControllerTest {
    @InjectMocks
    private MenuItemController menuItemController;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    public MenuItemControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddMenuItem_Success() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant(restaurantId, "testRestaurant", new Location(12.9715987, 77.5945667,"Bangalore"));
        when(restaurantRepository.findById(restaurantId)).thenReturn(java.util.Optional.of(restaurant));
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1L,"Burger", 5.99, restaurant));
        menuItems.add(new MenuItem(2L,"Fries", 2.49, restaurant));
        MenuItemListRequest request = new MenuItemListRequest("email", "token", restaurantId, menuItems);
        when(menuItemRepository.saveAll(menuItems)).thenReturn(menuItems);

        Restaurant restaurant = new Restaurant(); // create a mock restaurant object
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        MenuItemResponse expectedResponse = new MenuItemResponse("Menu items added successfully");
        when(menuItemRepository.saveAll(anyIterable())).thenReturn(null);

        ResponseEntity<MenuItemResponse> responseEntity = menuItemController.addMenuItem(restaurantId, request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(menuItemRepository, times(1)).saveAll(menuItems);
    }
}