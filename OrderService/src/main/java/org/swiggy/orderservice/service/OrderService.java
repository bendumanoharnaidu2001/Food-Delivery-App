package org.swiggy.orderservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.orderservice.dto.request.OrderRequest;
import org.swiggy.orderservice.dto.response.OrderResponse;
import org.swiggy.orderservice.model.OrderStatus;

@Service
public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
}
