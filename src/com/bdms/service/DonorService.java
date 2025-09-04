package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DonorService {
<<<<<<< HEAD
    private final DonorDAO donorDAO;

    public DonorService() {
        this.donorDAO = new DonorDAO();
    }

    // ✅ Add donor
    public void addDonor(Donor donor) {
        donorDAO.addDonor(donor);
    }

    // ✅ Get all donors
    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }

    // ✅ Update donor
    public boolean updateDonor(int id, String phone, String city) {
        return donorDAO.updateDonor(id, phone, city);
    }

    // ✅ Delete donor
    public boolean deleteDonor(int id) {
        return donorDAO.deleteDonor(id);
=======
    private DonorDAO donorDAO = new DonorDAO();

    public List<Donor> listAllDonors() {
        return donorDAO.getAllDonors();
    }

    public boolean isEligibleToDonate(Donor donor) {
        LocalDate last = donor.getLastDonationDate();
        if (last == null) return true;  // never donated before
        long daysSince = ChronoUnit.DAYS.between(last, LocalDate.now());
        return daysSince >= 90;
>>>>>>> origin/main
    }

    // ✅ Search donors (merged from your version)
    public List<Donor> searchDonors(String bloodGroup, String city) {
        return donorDAO.searchDonors(bloodGroup, city);
    }
}
