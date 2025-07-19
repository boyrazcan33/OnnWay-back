package com.app.route.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private List<RouteStop> optimizedRoute;
    private String totalDistance;
    private String totalDuration;
    private double totalCost;
}