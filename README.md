Route Optimizer - City Tourism Routes
A Spring Boot application that creates optimized routes for tourists in Istanbul and Tallinn based on their preferences.
Features

Smart Route Planning: Creates the shortest walking route between attractions
Multiple Filters: Filter by activity type, budget, and trip duration
Real Distance Calculation: Uses OSRM API for accurate walking times
Two Cities: Support for Istanbul and Tallinn attractions

Tech Stack

Backend: Java 21, Spring Boot 3.5.3
Database: PostgreSQL
Route Calculation: OSRM (Open Source Routing Machine)
Build Tool: Maven

Quick Start
1. Clone Repository
   bashgit clone <repository-url>
   cd route
2. Setup Database
   Create PostgreSQL database and update application.properties:
   propertiesspring.datasource.url=jdbc:postgresql://localhost:5432/your_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   osrm.api.base-url=http://router.project-osrm.org
3. Run Application
   bash./mvnw spring-boot:run
   Application will start on http://localhost:8080
   API Endpoints
   Create Optimized Route
   httpPOST /api/route/create
   Content-Type: application/json

{
"startLat": 41.0369,
"startLon": 28.9850,
"city": "ISTANBUL",
"activity": "FOOD",
"budget": "BUDGET",
"duration": "SHORT"
}
Get Attractions
httpGET /api/attractions?city=ISTANBUL&activity=FOOD&budget=BUDGET
Parameters
Cities

ISTANBUL - Istanbul, Turkey
TALLINN - Tallinn, Estonia

Activity Types

FOOD - Restaurants and food spots
ART_HISTORY - Museums and historical sites
SOCIAL_MEDIA - Instagram-worthy locations
ADVENTURE - Outdoor activities

Budget Ranges

BUDGET - Low cost options
MID_RANGE - Medium price range
PREMIUM - High-end experiences

Duration

SHORT - 3-4 hours
MEDIUM - 1 day
LONG - 2+ days

Example Response
json{
"optimizedRoute": [
{
"order": 1,
"name": "Sultanahmet Köftecisi",
"address": "Alemdar Mahallesi, Divanyolu Caddesi",
"description": "Traditional köfte restaurant since 1920",
"estimatedDuration": 30,
"walkingTime": "12 min",
"cost": 0.5
}
],
"totalDistance": "2.1 km",
"totalDuration": "3h 50m",
"totalCost": 17.5
}
Project Structure
route/
├── src/main/java/com/app/route/
│   ├── controller/     # REST controllers
│   ├── service/        # Business logic
│   ├── model/          # JPA entities
│   ├── repository/     # Data access
│   ├── dto/            # Request/Response objects
│   └── util/           # OSRM distance calculator
└── src/main/resources/
└── attractions.json # Sample data
Algorithm
Uses Nearest Neighbor algorithm:

Start from user's location
Find closest unvisited attraction
Move to that attraction
Repeat until all attractions visited
Apply duration filter to stay within time limits

Dependencies

Spring Boot Starter Web
Spring Boot Starter Data JPA
PostgreSQL Driver
Lombok
Jackson (JSON processing)