package org.swiggy.catalogservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.catalogservice.dto.request.MenuItemIdsRequest;
import org.swiggy.catalogservice.dto.request.MenuItemListRequest;
import org.swiggy.catalogservice.dto.request.MenuItemRequest;
import org.swiggy.catalogservice.dto.response.MenuItemListResponse;
import org.swiggy.catalogservice.dto.response.MenuItemResponse;
import org.swiggy.catalogservice.execptions.RestaurantNotFoundException;

@Service
public interface MenuItemService {
    MenuItemResponse addMenuItem(Long restaurantId, MenuItemListRequest menuItemListRequest);
    MenuItemResponse updateMenuItem(Long menuItemId, MenuItemRequest request);
    MenuItemListResponse getMenuItems(MenuItemIdsRequest request) throws RestaurantNotFoundException;
}
