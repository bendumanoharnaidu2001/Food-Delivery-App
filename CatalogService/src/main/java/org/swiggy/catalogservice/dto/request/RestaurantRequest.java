package org.swiggy.catalogservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {
    String email;
    String token;
    String name;
    Location location;
    List<MenuItem> menuItems;
}
