package org.swiggy.userservice.model.dto;

import lombok.Data;

@Data
public class Location {
    private double latitude;
    private double longitude;
    private String city;
}
