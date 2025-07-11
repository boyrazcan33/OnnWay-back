package com.app.route.dto;

import com.app.route.model.Customer;

import java.util.List;

public class RouteRequest {
    private List<Customer> customers;
    private double startLat;
    private double startLon;

    public RouteRequest() {}

    public List<Customer> getCustomers() { return customers; }
    public void setCustomers(List<Customer> customers) { this.customers = customers; }
    public double getStartLat() { return startLat; }
    public void setStartLat(double startLat) { this.startLat = startLat; }
    public double getStartLon() { return startLon; }
    public void setStartLon(double startLon) { this.startLon = startLon; }
}