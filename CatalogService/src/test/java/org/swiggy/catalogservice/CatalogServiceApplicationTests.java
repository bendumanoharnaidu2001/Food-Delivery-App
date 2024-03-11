package org.swiggy.catalogservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.catalogservice.dto.request.RestaurantRequest;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;
import org.swiggy.catalogservice.model.entite.Restaurant;
import org.swiggy.catalogservice.service.RestaurantServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogServiceApplicationTests {
}
