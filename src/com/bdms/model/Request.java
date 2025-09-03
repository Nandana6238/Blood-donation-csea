package com.bdms.model;

import java.time.LocalDate;

public class Request {
    private int id;                 // Auto-increment ID from DB
    private String recipientName;   // Who needs blood
    private String bloodGroup;      // Requested blood group
    private String city;            // Location
    private LocalDate requestDate;  // Date of request

    // Constructor for new request (before DB assigns id)
    public Request(String recipientName, String bloodGroup, String city, LocalDate requestDate) {
        this.recipientName = recipientName;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.requestDate = requestDate;
    }

    // Constructor with ID (when reading from DB)
    public Request(int id, String recipientName, String bloodGroup, String city, LocalDate requestDate) {
        this.id = id;
        this.recipientName = recipientName;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.requestDate = requestDate;
    }

    // Getters
    public int getId() { return id; }
    public String getRecipientName() { return recipientName; }
    public String getBloodGroup() { return bloodGroup; }
    public String getCity() { return city; }
    public LocalDate getRequestDate() { return requestDate; }

    // Setters (only if needed, otherwise keep immutable)
    public void setId(int id) { this.id = id; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public void setCity(String city) { this.city = city; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    @Override
    public String toString() {
        return "Request [ID=" + id +
               ", Recipient=" + recipientName +
               ", BloodGroup=" + bloodGroup +
               ", City=" + city +
               ", Date=" + requestDate + "]";
    }
}
