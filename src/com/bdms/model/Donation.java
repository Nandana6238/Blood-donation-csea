package com.bdms.model;

import java.time.LocalDate;

public class Donation {
    private int id;
    private int donorId;
    private LocalDate date;
    private int volumeMl;

    public Donation(int donorId, LocalDate date, int volumeMl) {
        this.donorId = donorId;
        this.date = date;
        this.volumeMl = volumeMl;
    }

    public Donation(int id, int donorId, LocalDate date, int volumeMl) {
        this.id = id;
        this.donorId = donorId;
        this.date = date;
        this.volumeMl = volumeMl;
    }

    public int getId() {
        return id;
    }

    public int getDonorId() {
        return donorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }

    @Override
    public String toString() {
        return String.format("Donation{id=%d, donorId=%d, date=%s, volume=%dml}", id, donorId, date, volumeMl);
    }
}
