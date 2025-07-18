package com.app.route.dto;

import com.app.route.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    private double startLat;
    private double startLon;
    private City city;
    private ActivityType activity;
    private BudgetRange budget;
    private Duration duration;
}