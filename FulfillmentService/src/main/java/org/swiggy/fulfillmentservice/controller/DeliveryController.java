package org.swiggy.fulfillmentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swiggy.fulfillmentservice.dto.request.DeliveryExecutiveRequest;
import org.swiggy.fulfillmentservice.service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @PostMapping("")
    public ResponseEntity<Long> getDeliveryPartner(@RequestBody DeliveryExecutiveRequest request) {
        return new ResponseEntity<>(deliveryService.getNearestDeliveryPartner(request), HttpStatus.OK);
    }
}
