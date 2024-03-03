package org.swiggy.orderservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuItemIdsRequest {
    List<Long> menuItemIds;
    Long restaurantId;
}
