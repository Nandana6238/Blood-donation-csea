package com.bdms.model;

import java.time.LocalDate;

public class Donation {
    private int id;
    private int donorId;
    private LocalDate donationDate;
    private int volumeMl;

    // Constructor for inserting a new donation (no ID yet, DB will auto-generate)
    public Donation(int donorId, LocalDate donationDate, int volumeMl) {
        this.donorId = donorId;
        this.donationDate = donationDate;
        this.volumeMl = volumeMl;
    }

    // Constructor with ID (useful when retrieving from DB)
    public Donation(int id, int donorId, LocalDate donationDate, int volumeMl) {
        this.id = id;
        this.donorId = donorId;
        this.donationDate = donationDate;
        this.volumeMl = volumeMl;
    }

    // Getters
    public int getId() { return id; }
    public int getDonorId() { return donorId; }
    public LocalDate getDonationDate() { return donationDate; }
    public int getVolumeMl() { return volumeMl; }

    @Override
    public String toString() {
        return "Donation [ID=" + id +
               ", DonorID=" + donorId +
               ", Date=" + donationDate +
               ", Volume=" + volumeMl + "ml]";
    }
}
