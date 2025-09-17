package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;

import java.util.List;
import java.util.Map;

public class DonorService {
    private final DonorDAO donorDAO;

    public DonorService(boolean mockMode) {
        this.donorDAO = new DonorDAO(mockMode);
    }

    /** Add donor (true if success, false if duplicate/error) */
    public boolean addDonor(Donor donor) {
        return donorDAO.addDonor(donor);
    }

    /** Get all donors */
    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }

    /** Search donors by blood group + city */
    public List<Donor> searchDonors(String bloodGroup, String city) {
        return donorDAO.searchDonors(bloodGroup, city);
    }

    /** Update donor by ID (phone + city) */
    public boolean updateDonor(int id, String phone, String city) {
        return donorDAO.updateDonor(id, phone, city);
    }

    /** Delete donor by ID */
    public boolean deleteDonor(int id) {
        return donorDAO.deleteDonor(id);
    }

    /** Find donor by phone */
    public Donor findByPhone(String phone) {
        return donorDAO.getDonorByPhone(phone);
    }

    // === Week 5 Enhancements ===

    /** Get donors by city */
    public List<Donor> getDonorsByCity(String city) {
        return donorDAO.getDonorsByCity(city);
    }

    /** Get donors by blood group */
    public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
        return donorDAO.getDonorsByBloodGroup(bloodGroup);
    }

    /** Get eligible donors (who can donate after 'months') */
    public List<Donor> getEligibleDonors(int months) {
        return donorDAO.getEligibleDonors(months);
    }

    // === Week 6 Enhancements ===

    /** Count donors grouped by blood group */
    public Map<String, Integer> countDonorsByBloodGroup() {
        return donorDAO.countDonorsByBloodGroup();
    }

    /** ✅ Count donors grouped by city and blood group */
    public Map<String, Integer> countDonorsByCityAndBloodGroup() {
        return donorDAO.countDonorsByCityAndBloodGroup();
    }

    /** Count eligible donors (efficient DB count) */
    public int countEligibleDonors(int months) {
        return donorDAO.countEligibleDonors(months);
    }
}
