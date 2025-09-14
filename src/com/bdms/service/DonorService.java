package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import java.time.LocalDate;
import java.util.List;

public class DonorService {
    private DonorDAO donorDAO = new DonorDAO();

    public List<Donor> listAllDonors() {
        return donorDAO.getAllDonors();
    }

    public boolean isEligibleToDonate(Donor donor) {
        LocalDate lastDonation = donor.getLastDonationDate();

        if (lastDonation == null) return true;

        return lastDonation.plusMonths(3).isBefore(LocalDate.now()) ||
               lastDonation.plusMonths(3).isEqual(LocalDate.now());
    }
}

