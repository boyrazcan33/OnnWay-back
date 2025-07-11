package com.app.route.service;

import com.app.route.model.Customer;
import com.app.route.util.DistanceCalculator;

import java.util.*;

public class RouteOptimizer {

    // Main optimization method using Nearest Neighbor algorithm
    public List<Customer> optimizeRoute(List<Customer> customers, double startLat, double startLon) {
        if (customers == null || customers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Customer> unvisited = new ArrayList<>(customers);
        List<Customer> optimizedRoute = new ArrayList<>();

        double currentLat = startLat;
        double currentLon = startLon;

        // Keep finding nearest customer until all are visited
        while (!unvisited.isEmpty()) {
            Customer nearest = findNearestCustomer(unvisited, currentLat, currentLon);
            optimizedRoute.add(nearest);
            unvisited.remove(nearest);

            // Move to the customer we just added
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
        }

        return optimizedRoute;
    }

    // Finds the closest customer from current position
    private Customer findNearestCustomer(List<Customer> customers, double currentLat, double currentLon) {
        Customer nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Customer customer : customers) {
            double distance = DistanceCalculator.calculateDistance(
                    currentLat, currentLon,
                    customer.getLatitude(), customer.getLongitude()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearest = customer;
            }
        }

        return nearest;
    }
}
