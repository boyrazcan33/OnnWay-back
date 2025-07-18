package com.app.route.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "attractions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String description;
    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ActivityType activityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_range")
    private BudgetRange budgetRange;

    @Column(name = "estimated_duration")
    private int estimatedDuration;

    @Column(name = "avg_cost")
    private double avgCost;
}