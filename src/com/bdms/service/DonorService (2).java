package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import java.util.List;

public class DonorService {
    private final DonorDAO dao = new DonorDAO();

    public List<Donor> search(String bloodGroup, String city) {
        return dao.searchDonors(bloodGroup, city);
    }
}
