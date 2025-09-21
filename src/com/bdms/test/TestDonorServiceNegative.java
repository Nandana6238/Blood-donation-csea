package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.List;

public class TestDonorServiceNegative {
    public static void main(String[] args) {
        System.out.println("=== MOCK Negative Tests ===");
        DonorService sMock = new DonorService(true);

        Donor d1 = new Donor(0, "MockX", 30, "M", "O+", "1111111111", "City", LocalDate.now());
        boolean added1 = sMock.addDonor(d1);
        boolean added2 = sMock.addDonor(new Donor(0, "MockY", 28, "F", "A+", "1111111111", "City", null));

        System.out.println("Add first donor: " + added1); // expect true
        System.out.println("Add duplicate phone donor: " + added2); // expect false

        System.out.println("\n=== DB Negative Tests ===");
        DonorService sDb = new DonorService(false);

        // Use a test phone number that is not in your real data
        String testPhone = "9998887776";

        Donor db1 = new Donor(0, "DBX", 35, "M", "AB+", testPhone, "Delhi", LocalDate.now());
        boolean dbAdd1 = sDb.addDonor(db1);
        boolean dbAdd2 = sDb.addDonor(new Donor(0, "DBY", 40, "F", "B+", testPhone, "Kochi", null));

        System.out.println("DB add first time: " + dbAdd1); // expect true
        System.out.println("DB add duplicate phone: " + dbAdd2); // expect false

        // cleanup: delete test donor
        if (dbAdd1) {
            List<Donor> found = sDb.searchDonors("AB+", "Delhi");
            if (!found.isEmpty()) {
                sDb.deleteDonor(found.get(0).getId());
                System.out.println("Cleaned up test donor.");
            }
        }
    }
}
