package com.app.route.controller;

import com.app.route.dto.RouteRequest;
import com.app.route.model.Customer;
import com.app.route.service.RouteOptimizer;
import com.app.route.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RouteOptimizer routeOptimizer;

    @PostMapping("/optimize")
    public ResponseEntity<?> optimizeRoute(@RequestBody RouteRequest request) {
        try {
            List<Customer> optimizedRoute = routeOptimizer.optimizeRoute(
                    request.getCustomers(),
                    request.getStartLat(),
                    request.getStartLon()
            );
            return ResponseEntity.ok(optimizedRoute);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Route optimization failed: " + e.getMessage());
        }
    }

    @PostMapping("/optimize-all")
    public ResponseEntity<?> optimizeAllCustomers(@RequestParam double startLat, @RequestParam double startLon) {
        try {
            List<Customer> allCustomers = customerService.getAllCustomers();
            List<Customer> optimizedRoute = routeOptimizer.optimizeRoute(allCustomers, startLat, startLon);
            return ResponseEntity.ok(optimizedRoute);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Route optimization failed: " + e.getMessage());
        }
    }
}