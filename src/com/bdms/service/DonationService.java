package com.bdms.service;

import com.bdms.dao.DonationDAO;
import com.bdms.model.Donation;
import java.time.LocalDate;
import java.util.List;

public class DonationService {
    private DonationDAO donationDAO;

    // Constructor
    public DonationService() {
        this.donationDAO = new DonationDAO();
    }

    // Record a donation
    public void recordDonation(int donorId, LocalDate date, int volumeMl) {
        Donation donation = new Donation(donorId, date, volumeMl);
        donationDAO.addDonation(donation);
        System.out.println("✅ Donation recorded successfully!");
    }

    // Show donation history of a donor
    public void showDonationHistory(int donorId) {
        List<Donation> history = donationDAO.getDonationHistory(donorId);
        if (history.isEmpty()) {
            System.out.println("ℹ No donation history found.");
        } else {
            history.forEach(System.out::println);
        }
    }

    // Show all donations
    public void showAllDonations() {
        List<Donation> all = donationDAO.getAllDonations();
        if (all.isEmpty()) {
            System.out.println("ℹ No donations found in the system.");
        } else {
            all.forEach(System.out::println);
        }
    }

    // Optional: expose all donations for GUI
    public List<Donation> getAllDonations() {
        return donationDAO.getAllDonations();
    }
}
