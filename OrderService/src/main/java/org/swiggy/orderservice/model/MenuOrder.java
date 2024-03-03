package org.swiggy.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.swiggy.orderservice.util.StringListConverterUtil;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;

    @Embedded
    private Location userLocation;      //
    private Long restaurantId;

    @Convert(converter = StringListConverterUtil.class)
    @Column(columnDefinition = "TEXT")
    private List<MenuItem> menuItems;
    private double totalPrice;           //
    private Long deliveryExecutiveId;    //

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.CONFIRMED;
    private LocalDateTime createdTime;
}
