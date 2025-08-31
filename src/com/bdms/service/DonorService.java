package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import java.util.List;

public class DonorService {
    private DonorDAO donorDAO;

    public DonorService() {
        this.donorDAO = new DonorDAO();
    }

    // Add donor (if your DAO.addDonor returns boolean, change this to return boolean)
    public void addDonor(Donor donor) {
        donorDAO.addDonor(donor);
    }

    // Get all donors
    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }

    // Update donor — returns true if updated (assumes donorDAO.updateDonor returns boolean)
    public boolean updateDonor(int id, String phone, String city) {
        return donorDAO.updateDonor(id, phone, city);
    }

    // Delete donor — returns true if deleted (assumes donorDAO.deleteDonor returns boolean)
    public boolean deleteDonor(int id) {
        return donorDAO.deleteDonor(id);
    }
}
