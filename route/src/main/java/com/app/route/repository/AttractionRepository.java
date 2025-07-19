package com.app.route.repository;

import com.app.route.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    List<Attraction> findByCityAndActivityTypeAndBudgetRange(
            City city,
            ActivityType activityType,
            BudgetRange budgetRange
    );

    @Query("SELECT a FROM Attraction a WHERE a.city = :city")
    List<Attraction> findByCity(@Param("city") City city);

    @Query("SELECT a FROM Attraction a WHERE a.city = :city AND a.activityType = :activityType")
    List<Attraction> findByCityAndActivityType(
            @Param("city") City city,
            @Param("activityType") ActivityType activityType
    );
}