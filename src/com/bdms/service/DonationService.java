package com.bdms.service;

import com.bdms.dao.DonationDAO;
import com.bdms.model.Donation;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DonationService {

    private final DonationDAO donationDAO = new DonationDAO();

    // Constructor
    public DonationService() {
        // No need to reassign donationDAO since it's already initialized
    }

    // Record a donation
    public void recordDonation(int donorId, LocalDate date, int volumeMl) {
        Donation donation = new Donation(donorId, date, volumeMl);
        donationDAO.addDonation(donation);
    }

    public List<Donation> getDonationHistory(int donorId) {
        return donationDAO.getDonationHistory(donorId);
    }

    public List<Donation> getAllDonations() {
        return donationDAO.getAllDonations();
    }

    public boolean exportDonationSummaryCsv(String filePath) {
        List<Donation> list = getAllDonations();
        if (list.isEmpty())
            return false;
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write("id,donor_id,donation_date,volume_ml\n");
            for (Donation d : list) {
                fw.write(String.format("%d,%d,%s,%d\n", d.getId(), d.getDonorId(), d.getDate(), d.getVolumeMl()));
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting donation summary: " + e.getMessage());
            return false;
        }
    }

    // Optional: expose all donations for GUI
}
