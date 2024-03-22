package org.swiggy.userservice.grpcClient;

import fulfilmentservicegrpc.AssignDelivery;
import fulfilmentservicegrpc.DeliveryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swiggy.userservice.model.dto.Location;
import org.swiggy.userservice.model.entites.Users;

import java.util.ArrayList;
import java.util.List;

public class AssignDeliveryPartnerGrpcClient {
    private final static Logger log = LoggerFactory.getLogger(AssignDeliveryPartnerGrpcClient.class);

    public static AssignDelivery.DeliveryPartnerResponse assignDeliveryPartner(Location location, List<Users> deliveryPartners) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        DeliveryServiceGrpc.DeliveryServiceBlockingStub stub = DeliveryServiceGrpc.newBlockingStub(channel);

        fulfilmentservicegrpc.AssignDelivery.Location location1 = fulfilmentservicegrpc.AssignDelivery.Location.newBuilder()
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .build();
        AssignDelivery.DeliveryExecutiveRequest request = AssignDelivery.DeliveryExecutiveRequest.newBuilder()
                .setRestaurantLocation(location1)
                .build();

        for (int i=0; i<deliveryPartners.size(); i++) {
            Users user = deliveryPartners.get(i);
            fulfilmentservicegrpc.AssignDelivery.DeliveryPartner deliveryPartner = fulfilmentservicegrpc.AssignDelivery.DeliveryPartner.newBuilder()
                    .setId(user.getId())
                    .setLocation(fulfilmentservicegrpc.AssignDelivery.Location.newBuilder()
                            .setLatitude(user.getLocation().getLatitude())
                            .setLongitude(user.getLocation().getLongitude())
                            .build())
                    .build();

            request = request.toBuilder().addDeliveryPartners(i, deliveryPartner).build();
        }

        AssignDelivery.DeliveryPartnerResponse deliveryPartnerResponse = stub.getNearestDeliveryPartner(request);
        log.info("Server message: {}", deliveryPartnerResponse);
        channel.shutdown();
        return deliveryPartnerResponse;
    }
}
