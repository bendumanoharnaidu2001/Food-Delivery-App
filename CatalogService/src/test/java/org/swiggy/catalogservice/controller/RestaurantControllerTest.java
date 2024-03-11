package org.swiggy.catalogservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.catalogservice.dto.request.RestaurantRequest;
import org.swiggy.catalogservice.dto.response.MenuItemListResponse;
import org.swiggy.catalogservice.dto.response.RestaurantResponse;
import org.swiggy.catalogservice.execptions.NoMenuItemFoundException;
import org.swiggy.catalogservice.execptions.RestaurantNotFoundException;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;
import org.swiggy.catalogservice.model.entite.Restaurant;
import org.swiggy.catalogservice.service.RestaurantService;
import org.swiggy.catalogservice.util.UserUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private UserUtils userUtils;
    @InjectMocks
    private RestaurantController restaurantController;
    @Test
    public void testAddRestaurant_Unauthorized() {
        RestaurantRequest request = new RestaurantRequest("test@gmail.com", "invalidToken", "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"), Collections.emptyList());

        when(userUtils.validateToken("test@gmail.com", "invalidToken")).thenReturn(false);

        ResponseEntity<RestaurantResponse> responseEntity = restaurantController.addRestaurant(request);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        verify(userUtils, times(1)).validateToken("test@gmail.com", "invalidToken");
        verifyNoInteractions(restaurantService);
    }
    @Test
    public void testAddRestaurant_success() {
        RestaurantRequest request = new RestaurantRequest("test@gmail.com", "validToken", "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"), Collections.emptyList());
        when(userUtils.validateToken("test@gmail.com", "validToken")).thenReturn(true);

        RestaurantResponse expectedResponse = new RestaurantResponse("Restaurant added successfully");

        when(restaurantService.createRestaurant(request)).thenReturn(expectedResponse);

        ResponseEntity<RestaurantResponse> responseEntity = restaurantController.addRestaurant(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(userUtils, times(1)).validateToken("test@gmail.com", "validToken");
        verify(restaurantService, times(1)).createRestaurant(request);
    }

    @Test
    public void testUpdateRestaurant_Success() throws RestaurantNotFoundException {
        // Mocking valid token scenario
        Long restaurantId = 1L;
        RestaurantRequest request = new RestaurantRequest("test@gmail.com", "validToken", "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"), Collections.emptyList());
        when(userUtils.validateToken("test@gmail.com", "validToken")).thenReturn(true);

        RestaurantResponse expectedResponse = new RestaurantResponse("Restaurant updated successfully");
        when(restaurantService.updateRestaurant(restaurantId, request)).thenReturn(expectedResponse);

        ResponseEntity<RestaurantResponse> responseEntity = restaurantController.updateRestaurant(restaurantId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(userUtils, times(1)).validateToken("test@gmail.com", "validToken");
        verify(restaurantService, times(1)).updateRestaurant(restaurantId, request);
    }

    @Test
    public void testUpdateRestaurant_Unauthorized() throws RestaurantNotFoundException {
        Long restaurantId = 1L;
        RestaurantRequest request = new RestaurantRequest("test@gmail.com", "invalidToken", "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"), Collections.emptyList());
        when(userUtils.validateToken("test@gmail.com", "invalidToken")).thenReturn(false);

        ResponseEntity<RestaurantResponse> responseEntity = restaurantController.updateRestaurant(restaurantId, request);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        verify(userUtils, times(1)).validateToken("test@gmail.com", "invalidToken");
        verifyNoInteractions(restaurantService);
    }

    @Test
    public void testUpdateRestaurant_NotFound() throws RestaurantNotFoundException {
        Long restaurantId = 1L;
        RestaurantRequest request = new RestaurantRequest("test@gmail.com", "validToken", "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"), Collections.emptyList());
        when(userUtils.validateToken("test@gmail.com", "validToken")).thenReturn(true);

        when(restaurantService.updateRestaurant(restaurantId, request)).thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        assertThrows(RestaurantNotFoundException.class, () -> {
             restaurantController.updateRestaurant(restaurantId, request);
        });

        verify(userUtils, times(1)).validateToken("test@gmail.com", "validToken");
        verify(restaurantService, times(1)).updateRestaurant(restaurantId, request);
    }

    @Test
    public void testGetListOfMenuItems_RestaurantFound() throws RestaurantNotFoundException, NoMenuItemFoundException {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant(restaurantId, "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"));
        RestaurantRequest request = new RestaurantRequest("test@gmail.com", "validToken", "Restaurant Name", new Location(12.9716, 77.5946, "Bengaluru"), Collections.emptyList());

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1L, "Burger", 5.99, restaurant));
        menuItems.add(new MenuItem(2L, "Fries", 2.49, restaurant));

        MenuItemListResponse expectedResponse = new MenuItemListResponse(menuItems, new Location(12.9716, 77.5946, "Bengaluru"));
        when(userUtils.validateToken("test@gmail.com", "validToken")).thenReturn(true);
        when(restaurantService.getListOfMenuItems(restaurantId)).thenReturn(expectedResponse);

        ResponseEntity<MenuItemListResponse> responseEntity = restaurantController.getListOfMenuItems(restaurantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMenuItems().size(), responseEntity.getBody().getMenuItems().size());

        for (int i = 0; i < expectedResponse.getMenuItems().size(); i++) {
            assertEquals(expectedResponse.getMenuItems().get(i), responseEntity.getBody().getMenuItems().get(i));
        }
        verify(restaurantService, times(1)).getListOfMenuItems(restaurantId);
    }
}