package org.swiggy.catalogservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.catalogservice.dto.request.RestaurantRequest;
import org.swiggy.catalogservice.dto.response.RestaurantResponse;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;
import org.swiggy.catalogservice.model.entite.Restaurant;
import org.swiggy.catalogservice.service.RestaurantService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private MenuItem menuItem;
    @Test
    void addRestaurant() throws Exception {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("KFC");
        request.setLocation(new Location(12.9715987,77.5945667));
        RestaurantResponse response = RestaurantResponse.builder().message("Restaurant added successfully").build();
        when(restaurantService.createRestaurant(request)).thenReturn(response);
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        verify(restaurantService,times(1)).createRestaurant(request);
    }
    @Test
    void addRestaurantWithListOfMenuItems() throws Exception {
        RestaurantRequest request = new RestaurantRequest();
        request.setName("Hotel");
        request.setLocation(new Location(12.9715987,77.5945667));
        MenuItem menuItem1 = new MenuItem(1L, "Chicken", 200, new Restaurant());
        MenuItem menuItem2 = new MenuItem(2L, "Mutton", 300, new Restaurant());

        request.setMenuItems(List.of(new MenuItem[]{menuItem1,menuItem2}));
        RestaurantResponse response = RestaurantResponse.builder().message("Restaurant added successfully").build();
        when(restaurantService.createRestaurant(request)).thenReturn(response);
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Restaurant added successfully"));
    }

    @Test
    void updateRestaurant() {

    }

    @Test
    void getListOfMenuItems() {
    }
}