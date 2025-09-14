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

    // Getters and Setters
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
        return id + " | " + name + " | " + age + " | " + gender + " | " +
               bloodGroup + " | " + phone + " | " + city + " | Last Donation: " + lastDonationDate;
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
