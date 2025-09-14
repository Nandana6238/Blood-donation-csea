package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;

import java.util.List;

public class DonorService {
    private DonorDAO donorDAO;

    public DonorService(boolean mockMode) {
        this.donorDAO = new DonorDAO(mockMode);
    }

    public List<Donor> searchDonors(String bloodGroup, String city) {
        return donorDAO.searchDonors(bloodGroup, city);
    }

    // FIX: return boolean instead of void
    public boolean addDonor(Donor donor) {
        return donorDAO.addDonor(donor);
    }

    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }

    public boolean updateDonor(int id, String phone, String city) {
        return donorDAO.updateDonor(id, phone, city);
    }

    public boolean deleteDonor(int id) {
        return donorDAO.deleteDonor(id);
    }

    public Donor findByPhone(String phone) {
        return donorDAO.getDonorByPhone(phone);
    }
}
