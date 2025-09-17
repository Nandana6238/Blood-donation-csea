package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.Map;

public class TestSummaryReports {
    public static void main(String[] args) {
        System.out.println("=== MOCK Summary Tests ===");
        DonorService s = new DonorService(true);

        // Add donors with city/blood group
        s.addDonor(new Donor(0, "M1", 30, "M", "A+", "1111111111", "Delhi", LocalDate.now().minusMonths(6)));
        s.addDonor(new Donor(0, "M2", 28, "F", "O+", "2222222222", "Delhi", null));

        // Add donor with null city and null blood group (should show "Not Specified")
        s.addDonor(new Donor(0, "M3", 25, "M", null, "3333333333", null, null));

        // --- Test 1: By Blood Group ---
        Map<String, Integer> byBg = s.countDonorsByBloodGroup();
        System.out.println("By BloodGroup (mock): " + byBg);

        // --- Test 2: By City + BloodGroup ---
        Map<String, Integer> byCityBg = s.countDonorsByCityAndBloodGroup();
        System.out.println("By City & BG (mock): " + byCityBg);

        // --- Test 3: Eligible donors ---
        int eligible = s.countEligibleDonors(3);
        System.out.println("Eligible (>=3 months) (mock): " + eligible);
    }
}
