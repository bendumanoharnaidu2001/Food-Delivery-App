package org.swiggy.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swiggy.orderservice.dto.enums.UserType;
import org.swiggy.orderservice.dto.request.OrderRequest;
import org.swiggy.orderservice.dto.request.ValidateTokenRequest;
import org.swiggy.orderservice.dto.response.OrderResponse;
import org.swiggy.orderservice.model.OrderStatus;
import org.swiggy.orderservice.service.OrderService;
import org.swiggy.orderservice.util.UserUtils;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserUtils userUtils;
    @PostMapping("")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        if(!userUtils.validateToken(request.getEmail(), request.getToken(), UserType.CUSTOMER)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        OrderResponse response = orderService.placeOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam("status") OrderStatus status, @RequestBody ValidateTokenRequest request) {
        if(!userUtils.validateToken(request.getEmail(), request.getToken(), UserType.DELIVERY_EXECUTIVE)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        OrderResponse response = orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
