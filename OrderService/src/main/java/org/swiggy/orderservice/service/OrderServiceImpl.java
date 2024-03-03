package org.swiggy.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.swiggy.orderservice.dto.request.MenuItemIdsRequest;
import org.swiggy.orderservice.dto.request.OrderRequest;
import org.swiggy.orderservice.dto.response.MenuItemListResponse;
import org.swiggy.orderservice.dto.response.OrderResponse;
import org.swiggy.orderservice.model.MenuItem;
import org.swiggy.orderservice.model.MenuOrder;
import org.swiggy.orderservice.model.OrderStatus;
import org.swiggy.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        String url = "http://localhost:8081/menu-items";
        RestTemplate restTemplate = new RestTemplate();
        List<Long> menuItemIds = request.getMenuItems().stream().map(MenuItem::getId).collect(Collectors.toList());
        MenuItemIdsRequest menuItemIdsRequest = MenuItemIdsRequest.builder().menuItemIds(menuItemIds).restaurantId(request.getRestaurantId()).build();
        ResponseEntity<MenuItemListResponse> response = restTemplate.postForEntity(url, menuItemIdsRequest, MenuItemListResponse.class);
        HashMap<Long,Integer> map = new HashMap<>();
        for(MenuItem menuItem : request.getMenuItems()) {
            map.put(menuItem.getId(),map.getOrDefault(menuItem.getId(),0)+menuItem.getQuantity());
        }
        AtomicReference<Double> totalBill = new AtomicReference<>( 0.0);
        response.getBody().getMenuItems().forEach(menuItem -> {
            if(map.containsKey(menuItem.getId())) {
                menuItem.setQuantity(map.get(menuItem.getId()));
                totalBill.updateAndGet(v -> (v + menuItem.getPrice() * menuItem.getQuantity()));
            }
        });
        url = "http://localhost:8080/users/assign-delivery-partner";
        Long deliveryPartnerId = restTemplate.postForEntity(url, response.getBody().getRestaurantLocation(), Long.class).getBody();

        MenuOrder menuOrder = MenuOrder.builder().userId(request.getUserId()).menuItems(response.getBody().getMenuItems())
                .restaurantId(request.getRestaurantId()).totalPrice(totalBill.get())
                .deliveryExecutiveId(deliveryPartnerId)
                .createdTime(LocalDateTime.now()).build();
        orderRepository.save(menuOrder);
        return OrderResponse.builder().message("Order placed successfully").build();
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        MenuOrder menuOrder = orderRepository.findById(orderId).orElse(null);
        if(menuOrder == null) {
            return OrderResponse.builder().message("Order not found").build();
        }
        menuOrder.setOrderStatus(status);
        orderRepository.save(menuOrder);
        return OrderResponse.builder().message("Order status updated successfully").build();
    }
}
