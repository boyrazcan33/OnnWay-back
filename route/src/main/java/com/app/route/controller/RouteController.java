package com.app.route.controller;

import com.app.route.model.Customer;
import com.app.route.service.RouteOptimizer;
import org.springframework.web.bind.annotation.*;
import com.app.route.dto.RouteRequest;


import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteController {

    // Endpoint to optimize customer visit route
    @PostMapping("/optimize")
    public List<Customer> optimizeRoute(@RequestBody RouteRequest request) {
        RouteOptimizer optimizer = new RouteOptimizer();
        return optimizer.optimizeRoute(
                request.getCustomers(),
                request.getStartLat(),
                request.getStartLon()
        );
    }
}