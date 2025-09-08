package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;

import java.util.List;

public class DonorService {
    private final DonorDAO donorDAO;

    public DonorService(boolean useMock) {
        this.donorDAO = new DonorDAO(useMock);
    }

    public List<Donor> searchDonors(String bloodGroup, String city) {
        return donorDAO.searchDonors(bloodGroup, city);
    }

    public void addDonor(Donor donor) {
        donorDAO.addDonor(donor);
    }

    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }
}
