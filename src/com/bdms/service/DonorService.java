package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DonorService {
    private DonorDAO donorDAO = new DonorDAO();

    public List<Donor> listAllDonors() {
        return donorDAO.getAllDonors();
    }

    public boolean isEligibleToDonate(Donor donor) {
        LocalDate last = donor.getLastDonationDate();
        if (last == null) return true;  // never donated before
        long daysSince = ChronoUnit.DAYS.between(last, LocalDate.now());
        return daysSince >= 90;
    }
}
