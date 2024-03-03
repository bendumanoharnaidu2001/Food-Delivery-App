package org.swiggy.fulfillmentservice.service;

import org.springframework.stereotype.Service;
import org.swiggy.fulfillmentservice.dto.request.DeliveryExecutiveRequest;
import org.swiggy.fulfillmentservice.dto.request.DeliveryPartner;
import org.swiggy.fulfillmentservice.dto.request.Location;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    @Override
    public Long getNearestDeliveryPartner(DeliveryExecutiveRequest request) {
        Location location = request.getRestaurantLocation();
        Long deliveryPartnerId = null;
        Double minDistance = Double.MAX_VALUE;
        for(DeliveryPartner partner : request.getDeliveryPartners()) {
            if(minDistance > calculateDistance(location, partner.getLocation())) {
                minDistance = calculateDistance(location, partner.getLocation());
                deliveryPartnerId = partner.getId();
            }
        }
        return deliveryPartnerId;
    }
    public static double calculateDistance(Location location1, Location location2) {
        // Calculate the horizontal and vertical differences
        double dx = location1.getLatitude() - location2.getLatitude();
        double dy = location1.getLongitude() - location2.getLatitude();

        // Calculate the distance using the distance formula
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

}
