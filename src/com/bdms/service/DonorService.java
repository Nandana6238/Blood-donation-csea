package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import java.util.List;

public class DonorService {
    private final DonorDAO donorDAO;

    public DonorService() {
        this.donorDAO = new DonorDAO(); // default mock
    }

    public DonorService(boolean useMock) {
        this.donorDAO = new DonorDAO(useMock);
    }

    public void addDonor(Donor donor) {
        donorDAO.addDonor(donor);
    }

    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }

    public boolean updateDonor(int id, String phone, String city) {
       // return donorDAO.updateDonor(id, phone, city);
    Donor donor = new Donor();
    donor.setId(id);
    donor.setPhone(phone);
    donor.setCity(city);
    return donorDAO.updateDonor(donor);
}
    

    public boolean deleteDonor(int id) {
        return donorDAO.deleteDonor(id);
    }

    public List<Donor> searchDonors(String bloodGroup, String city) {
        return donorDAO.searchDonors(bloodGroup, city);
    }
}
