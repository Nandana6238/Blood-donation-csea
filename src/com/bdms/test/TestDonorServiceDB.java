package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.List;

public class TestDonorServiceDB {
    public static void main(String[] args) {
        System.out.println("Running tests in DB mode...");
        DonorService service = new DonorService(false); // DB mode

        // 1) Add donor
        Donor donor = new Donor("DB User", 25, "M", "O+", "1234567890", "Delhi", LocalDate.now());
        service.addDonor(donor);
        System.out.println("[PASS] Added donor to DB");

        // 2) Search donor
        List<Donor> results = service.searchDonors("O+", "Delhi");
        if (!results.isEmpty()) {
            System.out.println("[PASS] Search O+ Delhi returned results");
        } else {
            System.out.println("[FAIL] Search O+ Delhi returned no results");
        }

        // 3) Update donor
        if (!results.isEmpty()) {
            Donor first = results.get(0);
            boolean updated = service.updateDonor(first.getId(), "1111111111", "Mumbai");
            System.out.println(updated ? "[PASS] Updated donor in DB" : "[FAIL] Update donor failed");
        }

        // 4) Delete donor
        if (!results.isEmpty()) {
            Donor first = results.get(0);
            boolean deleted = service.deleteDonor(first.getId());
            System.out.println(deleted ? "[PASS] Deleted donor from DB" : "[FAIL] Delete donor failed");
        }

        System.out.println("DB tests done.");
    }
}
