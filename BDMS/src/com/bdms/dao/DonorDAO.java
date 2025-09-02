package com.bdms.dao;

import com.bdms.model.Donor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DonorDAO {
    private static final List<Donor> donors = new ArrayList<>();

    static {
        donors.add(new Donor("Aisha Khan", 24, "F", "A+", "9876543210", "Delhi", LocalDate.of(2025, 5, 1)));
        donors.add(new Donor("Rahul Nair", 29, "M", "O+", "9876501234", "Kochi", LocalDate.of(2025, 7, 10)));
        donors.add(new Donor("Devika P", 32, "F", "A+", "9998887776", "Delhi", null));
        donors.add(new Donor("Arun Kumar", 41, "M", "B-", "9123456789", "Chennai", LocalDate.of(2025, 8, 10)));
    }

    public List<Donor> searchDonors(String bloodGroup, String city) {
        return donors.stream()
                .filter(d -> d.getBloodGroup().equalsIgnoreCase(bloodGroup)
                        && d.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}
