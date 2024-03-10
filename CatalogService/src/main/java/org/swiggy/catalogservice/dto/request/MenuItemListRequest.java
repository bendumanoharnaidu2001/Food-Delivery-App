package org.swiggy.catalogservice.dto.request;

import lombok.Data;
import org.swiggy.catalogservice.model.entite.MenuItem;

import java.util.List;
@Data
public class MenuItemListRequest {
    String email;
    String token;
    List<MenuItem> menuItems;
}
