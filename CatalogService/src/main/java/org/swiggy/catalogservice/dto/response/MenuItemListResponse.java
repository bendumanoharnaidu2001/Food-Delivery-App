package org.swiggy.catalogservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.catalogservice.model.dto.Location;
import org.swiggy.catalogservice.model.entite.MenuItem;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemListResponse {
    List<MenuItem> menuItems;
    private Location restaurantLocation;
}
