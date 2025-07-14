package com.app.route.service;

import com.app.route.model.Customer;
import com.app.route.util.RealDistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RouteOptimizer {

    @Autowired
    private RealDistanceCalculator realDistanceCalculator;

    public List<Customer> optimizeRoute(List<Customer> customers, double startLat, double startLon) {
        if (customers == null || customers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Customer> unvisited = new ArrayList<>(customers);
        List<Customer> optimizedRoute = new ArrayList<>();

        double currentLat = startLat;
        double currentLon = startLon;

        while (!unvisited.isEmpty()) {
            Customer nearest = findNearestCustomer(unvisited, currentLat, currentLon);
            optimizedRoute.add(nearest);
            unvisited.remove(nearest);

            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
        }

        return optimizedRoute;
    }

    private Customer findNearestCustomer(List<Customer> customers, double currentLat, double currentLon) {
        Customer nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Customer customer : customers) {
            try {
                double distance = realDistanceCalculator.getRoadDistance(
                        currentLat, currentLon,
                        customer.getLatitude(), customer.getLongitude()
                );

                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = customer;
                }
            } catch (Exception e) {
                throw new RuntimeException("Route optimization failed: " + e.getMessage());
            }
        }

        return nearest;
    }
}