package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.List;

public class TestDonorServiceManual {
    public static void main(String[] args) {
        System.out.println("Running manual tests in MOCK mode...");
        DonorService service = new DonorService(true); // mock mode

        // 1) search
        List<Donor> aPlusDelhi = service.searchDonors("A+", "Delhi");
        assertTrue(aPlusDelhi.size() >= 1, "Search A+ Delhi");

        // 2) add donor
        Donor newDonor = new Donor("Test User", 22, "F", "AB+", "9999999999", "Delhi", LocalDate.now());
        service.addDonor(newDonor);
        List<Donor> abPlusDelhi = service.searchDonors("AB+", "Delhi");
        assertTrue(abPlusDelhi.size() >= 1, "Add donor and search AB+ Delhi");

        // 3) update donor (if exists)
        List<Donor> all = service.getAllDonors();
        if (!all.isEmpty()) {
            Donor first = all.get(0);
            boolean updated = service.updateDonor(first.getId(), "1111111111", "NewCity");
            assertTrue(updated, "Update donor");
        }

        // 4) delete donor (if exists)
        if (!all.isEmpty()) {
            Donor last = all.get(all.size() - 1);
            boolean deleted = service.deleteDonor(last.getId());
            assertTrue(deleted, "Delete donor");
        }

        System.out.println("All manual tests done.");
    }

    private static void assertTrue(boolean cond, String testName) {
        if (cond)
            System.out.println("[PASS] " + testName);
        else
            System.out.println("[FAIL] " + testName);
    }
}
