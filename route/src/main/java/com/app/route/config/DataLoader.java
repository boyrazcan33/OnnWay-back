package com.app.route.config;

import com.app.route.model.*;
import com.app.route.repository.AttractionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;

@Component
public class DataLoader {

    @Autowired
    private AttractionRepository attractionRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        if (attractionRepository.count() == 0) {
            loadAttractionsFromJson();
        }
    }

    private void loadAttractionsFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("attractions.json");
            InputStream inputStream = resource.getInputStream();

            JsonNode rootNode = mapper.readTree(inputStream);
            JsonNode attractionsNode = rootNode.get("attractions");

            for (JsonNode attractionNode : attractionsNode) {
                Attraction attraction = new Attraction();
                attraction.setName(attractionNode.get("name").asText());
                attraction.setAddress(attractionNode.get("address").asText());
                attraction.setDescription(attractionNode.get("description").asText());
                attraction.setLatitude(attractionNode.get("latitude").asDouble());
                attraction.setLongitude(attractionNode.get("longitude").asDouble());
                attraction.setCity(City.valueOf(attractionNode.get("city").asText()));
                attraction.setActivityType(ActivityType.valueOf(attractionNode.get("activity_type").asText()));
                attraction.setBudgetRange(BudgetRange.valueOf(attractionNode.get("budget_range").asText()));
                attraction.setEstimatedDuration(attractionNode.get("estimated_duration").asInt());
                attraction.setAvgCost(attractionNode.get("avg_cost").asDouble());

                attractionRepository.save(attraction);
            }

            System.out.println("Loaded " + attractionRepository.count() + " attractions from JSON");

        } catch (Exception e) {
            System.err.println("Error loading attractions from JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}