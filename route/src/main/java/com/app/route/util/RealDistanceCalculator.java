package com.app.route.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class RealDistanceCalculator {

    @Value("${osrm.api.base-url}")
    private String osrmBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public double getRoadDistance(double lat1, double lon1, double lat2, double lon2) {
        String url = String.format(
                "%s/route/v1/driving/%f,%f;%f,%f?overview=false",
                osrmBaseUrl, lon1, lat1, lon2, lat2
        );

        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response == null || !response.has("routes") || response.get("routes").size() == 0) {
                throw new RuntimeException("No route found between coordinates");
            }

            double distanceMeters = response.get("routes").get(0).get("distance").asDouble();
            return distanceMeters / 1000; // Convert to kilometers
        } catch (Exception e) {
            throw new RuntimeException("Unable to calculate road distance: " + e.getMessage());
        }
    }
}