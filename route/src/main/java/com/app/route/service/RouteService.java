package com.app.route.service;

import com.app.route.dto.*;
import com.app.route.model.*;
import com.app.route.repository.AttractionRepository;
import com.app.route.util.RealDistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RouteService {

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private RealDistanceCalculator distanceCalculator;

    public RouteResponse createOptimizedRoute(RouteRequest request) {
        List<Attraction> candidates = attractionRepository.findByCityAndActivityTypeAndBudgetRange(
                request.getCity(),
                request.getActivity(),
                request.getBudget()
        );

        candidates = filterByDuration(candidates, request.getDuration());
        List<Attraction> optimizedRoute = optimizeRoute(candidates, request.getStartLat(), request.getStartLon());
        return buildRouteResponse(optimizedRoute, request.getStartLat(), request.getStartLon());
    }

    private List<Attraction> filterByDuration(List<Attraction> attractions, Duration duration) {
        int maxDuration = switch (duration) {
            case SHORT -> 240;
            case MEDIUM -> 480;
            case LONG -> 2880;
        };

        int totalDuration = 0;
        List<Attraction> filtered = new ArrayList<>();

        for (Attraction attraction : attractions) {
            if (totalDuration + attraction.getEstimatedDuration() <= maxDuration) {
                filtered.add(attraction);
                totalDuration += attraction.getEstimatedDuration();
            }
        }

        return filtered;
    }

    private List<Attraction> optimizeRoute(List<Attraction> attractions, double startLat, double startLon) {
        if (attractions.isEmpty()) return new ArrayList<>();

        List<Attraction> unvisited = new ArrayList<>(attractions);
        List<Attraction> optimized = new ArrayList<>();

        double currentLat = startLat;
        double currentLon = startLon;

        while (!unvisited.isEmpty()) {
            Attraction nearest = findNearestAttraction(unvisited, currentLat, currentLon);
            optimized.add(nearest);
            unvisited.remove(nearest);
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
        }

        return optimized;
    }

    private Attraction findNearestAttraction(List<Attraction> attractions, double currentLat, double currentLon) {
        Attraction nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Attraction attraction : attractions) {
            double distance = distanceCalculator.getRoadDistance(
                    currentLat, currentLon,
                    attraction.getLatitude(), attraction.getLongitude()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearest = attraction;
            }
        }

        return nearest;
    }

    private RouteResponse buildRouteResponse(List<Attraction> route, double startLat, double startLon) {
        List<RouteStop> stops = new ArrayList<>();
        double totalCost = 0;
        int totalDuration = 0;
        double totalDistance = 0;

        double currentLat = startLat;
        double currentLon = startLon;

        for (int i = 0; i < route.size(); i++) {
            Attraction attraction = route.get(i);

            double distance = distanceCalculator.getRoadDistance(
                    currentLat, currentLon,
                    attraction.getLatitude(), attraction.getLongitude()
            );

            totalDistance += distance;
            String walkingTime = Math.round(distance * 12) + " min";

            RouteStop stop = new RouteStop(
                    i + 1,
                    attraction.getName(),
                    attraction.getAddress(),
                    attraction.getDescription(),
                    attraction.getEstimatedDuration(),
                    walkingTime,
                    attraction.getAvgCost()
            );

            stops.add(stop);
            totalCost += attraction.getAvgCost();
            totalDuration += attraction.getEstimatedDuration();

            currentLat = attraction.getLatitude();
            currentLon = attraction.getLongitude();
        }

        return new RouteResponse(
                stops,
                String.format("%.1f km", totalDistance),
                formatDuration(totalDuration),
                Math.round(totalCost * 100.0) / 100.0
        );
    }

    private String formatDuration(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        if (hours > 0) {
            return hours + "h " + mins + "m";
        }
        return mins + "m";
    }
}