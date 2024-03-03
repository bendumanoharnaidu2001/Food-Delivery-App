package org.swiggy.orderservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    String message;
}
