package org.swiggy.catalogservice.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class MenuItemIdsRequest {
    List<Long> menuItemIds;
    Long restaurantId;
}
