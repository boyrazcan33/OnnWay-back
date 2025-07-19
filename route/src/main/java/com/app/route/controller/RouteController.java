package com.app.route.controller;

import com.app.route.dto.*;
import com.app.route.model.*;
import com.app.route.service.RouteService;
import com.app.route.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private AttractionRepository attractionRepository;

    @PostMapping("/route/create")
    public ResponseEntity<RouteResponse> createRoute(@RequestBody RouteRequest request) {
        try {
            RouteResponse response = routeService.createOptimizedRoute(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/attractions")
    public ResponseEntity<List<Attraction>> getAttractions(
            @RequestParam City city,
            @RequestParam(required = false) ActivityType activity,
            @RequestParam(required = false) BudgetRange budget,
            @RequestParam(required = false) Duration duration
    ) {
        try {
            List<Attraction> attractions;

            if (activity != null && budget != null) {
                attractions = attractionRepository.findByCityAndActivityTypeAndBudgetRange(city, activity, budget);
            } else if (activity != null) {
                attractions = attractionRepository.findByCityAndActivityType(city, activity);
            } else {
                attractions = attractionRepository.findByCity(city);
            }

            // Duration filtresi ekle
            if (duration != null && !attractions.isEmpty()) {
                attractions = filterByDuration(attractions, duration);
            }

            return ResponseEntity.ok(attractions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Duration filtreleme method'u
    private List<Attraction> filterByDuration(List<Attraction> attractions, Duration duration) {
        int maxDuration = switch (duration) {
            case SHORT -> 240;   // 4 saat
            case MEDIUM -> 480;  // 8 saat (1 gün)
            case LONG -> 2880;   // 48 saat (2 gün)
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
}