package com.app.route.controller;

import com.app.route.dto.RouteRequest;
import com.app.route.model.Customer;
import com.app.route.service.RouteOptimizer;
import com.app.route.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/optimize")
    public List<Customer> optimizeRoute(@RequestBody RouteRequest request) {
        RouteOptimizer optimizer = new RouteOptimizer();
        return optimizer.optimizeRoute(
                request.getCustomers(),
                request.getStartLat(),
                request.getStartLon()
        );
    }

    // Get all customers and optimize them from a starting point
    @PostMapping("/optimize-all")
    public List<Customer> optimizeAllCustomers(@RequestParam double startLat, @RequestParam double startLon) {
        List<Customer> allCustomers = customerService.getAllCustomers();
        RouteOptimizer optimizer = new RouteOptimizer();
        return optimizer.optimizeRoute(allCustomers, startLat, startLon);
    }
}