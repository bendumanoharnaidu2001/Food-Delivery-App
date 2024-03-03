package org.swiggy.fulfillmentservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.fulfillmentservice.dto.request.DeliveryExecutiveRequest;

@Service
public interface DeliveryService {
    Long getNearestDeliveryPartner(DeliveryExecutiveRequest request);
}
