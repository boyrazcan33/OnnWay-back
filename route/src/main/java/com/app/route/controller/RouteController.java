package com.app.route.controller;

import com.app.route.dto.*;
import com.app.route.model.*;
import com.app.route.service.RouteService;
import com.app.route.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            @RequestParam(required = false) BudgetRange budget
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

            return ResponseEntity.ok(attractions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}