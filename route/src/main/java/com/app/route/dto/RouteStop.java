package com.app.route.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteStop {
    private int order;
    private String name;
    private String address;
    private String description;
    private int estimatedDuration;
    private String walkingTime;
    private double cost;
}