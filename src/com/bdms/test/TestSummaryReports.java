package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.Map;

public class TestSummaryReports {
    public static void main(String[] args) {
        System.out.println("=== MOCK Summary Tests ===");
        DonorService service = new DonorService(true);

        // Add mock donors
        service.addDonor(new Donor(0, "M1", 30, "M", "A+", "1111111111", "Delhi", LocalDate.now().minusMonths(6)));
        service.addDonor(new Donor(0, "M2", 28, "F", "O+", "2222222222", "Delhi", null));
        service.addDonor(new Donor(0, "M3", 35, "M", "B-", "3333333333", "Chennai", LocalDate.now().minusMonths(4)));
        service.addDonor(new Donor(0, "M4", 22, "F", "A+", "4444444444", "Chennai", null));

        // Test summary by blood group
        Map<String, Integer> byBg = service.countDonorsByBloodGroup();
        System.out.println("By Blood Group: " + byBg);

        // Test summary by city & blood group
        Map<String, Integer> byCityBg = service.countDonorsByCityAndBloodGroup();
        System.out.println("By City & Blood Group: " + byCityBg);

        // Test eligible donors (>= 3 months)
        int eligible = service.countEligibleDonors(3);
        System.out.println("Eligible (>=3 months): " + eligible);
    }
}
