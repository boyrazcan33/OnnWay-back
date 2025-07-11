package com.app.route.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cari_ref")
    private String cariRef;

    private double latitude;
    private double longitude;

    public Customer() {}

    public Customer(String cariRef, double latitude, double longitude) {
        this.cariRef = cariRef;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCariRef() { return cariRef; }
    public void setCariRef(String cariRef) { this.cariRef = cariRef; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}