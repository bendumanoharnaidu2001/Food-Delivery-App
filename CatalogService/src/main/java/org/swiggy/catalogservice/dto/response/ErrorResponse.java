package org.swiggy.catalogservice.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private long timestamp;
}
