package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import com.bdms.util.ImportResult;

import java.util.List;
import java.util.Map;

public class DonorService {
    private final DonorDAO dao;

    public DonorService(boolean mockMode) {
        this.dao = new DonorDAO(mockMode);
    }

    // --- CRUD wrappers ---

    public boolean addDonor(Donor donor) {
        return dao.addDonor(donor);
    }

    public List<Donor> getAllDonors() {
        return dao.getAllDonors();
    }

    public boolean updateDonor(int id, String phone, String city) {
        return dao.updateDonor(id, phone, city);
    }

    public boolean deleteDonor(int id) {
        return dao.deleteDonor(id);
    }

    public Donor findByPhone(String phone) {
        return dao.getDonorByPhone(phone);
    }

    // --- Reports wrappers ---

    public List<Donor> getDonorsByCity(String city) {
        return dao.getDonorsByCity(city);
    }

    public List<Donor> getDonorsByBloodGroup(String bg) {
        return dao.getDonorsByBloodGroup(bg);
    }

    public List<Donor> getEligibleDonors(int months) {
        return dao.getEligibleDonors(months);
    }

    public Map<String, Integer> countDonorsByBloodGroup() {
        return dao.countDonorsByBloodGroup();
    }

    public Map<String, Integer> countDonorsByCityAndBloodGroup() {
        return dao.countDonorsByCityAndBloodGroup();
    }

    public int countEligibleDonors(int months) {
        return dao.countEligibleDonors(months);
    }

    // --- Week 7: CSV Import wrapper ---
    public ImportResult importDonorsFromCsv(String filename) {
        return dao.importDonorsFromCsv(filename);
    }
}