package org.swiggy.catalogservice.dto.request;

import lombok.Data;
import org.swiggy.catalogservice.model.entite.MenuItem;
@Data
public class MenuItemRequest {
    String email;
    String token;
    MenuItem menuItem;
}
