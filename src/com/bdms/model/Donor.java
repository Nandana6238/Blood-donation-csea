package com.bdms.model;

import java.time.LocalDate;

public class Donor {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String phone;
    private String city;
    private LocalDate lastDonationDate;

    // Default constructor
    public Donor() { }

    // Constructor with all fields (including ID)
    public Donor(int id, String name, int age, String gender, String bloodGroup,
                 String phone, String city, LocalDate lastDonationDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
        this.lastDonationDate = lastDonationDate;
    }

    // Constructor without ID (for new donors before saving to DB)
    public Donor(String name, int age, String gender, String bloodGroup,
                 String phone, String city, LocalDate lastDonationDate) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.city = city;
        this.lastDonationDate = lastDonationDate;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }

    @Override
    public String toString() {
        return "Donor [ID=" + id + ", Name=" + name + ", Age=" + age +
               ", Gender=" + gender + ", BloodGroup=" + bloodGroup +
               ", Phone=" + phone + ", City=" + city +
               ", LastDonationDate=" + lastDonationDate + "]";
    }
}

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DonorService {
    public boolean isEligibleToDonate(Donor donor) {
        if (donor.getAge() < 18 || donor.getAge() > 60) return false; 
        if (donor.getLastDonationDate() == null) return true; 

        long days = ChronoUnit.DAYS.between(donor.getLastDonationDate(), LocalDate.now());
        return days >= 90; 
    }
}
