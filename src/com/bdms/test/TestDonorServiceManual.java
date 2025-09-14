package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;

public class TestDonorServiceManual {
    public static void main(String[] args) {
        boolean useMock = true; // true = Mock Mode, false = DB Mode
        DonorService service = new DonorService(useMock);

        System.out.println("=== Running Manual Tests (" + (useMock ? "MOCK" : "DB") + ") ===");

        // ---------------- Positive Tests ----------------
        System.out.println("\n[TEST] Search A+ Delhi");
        System.out.println(service.searchDonors("A+", "Delhi"));

        System.out.println("\n[TEST] Add donor");
        boolean added = service.addDonor(new Donor(
                0, "Test User", 25, "F", "AB+",
                "9999999999", "Delhi", LocalDate.now()));
        System.out.println("Add donor result: " + added);
        System.out.println("Search AB+ Delhi after adding:");
        System.out.println(service.searchDonors("AB+", "Delhi"));

        System.out.println("\n[TEST] Get all donors");
        service.getAllDonors().forEach(System.out::println);

        // ---------------- Negative Tests ----------------
        System.out.println("\n❌ [NEGATIVE TEST] Add donor with duplicate phone");
        boolean addedDuplicate = service.addDonor(new Donor(
                0, "Duplicate User", 30, "M", "O+",
                "9999999999", "Kochi", null));
        System.out.println("Add duplicate donor result (expect false): " + addedDuplicate);

        System.out.println("\n❌ [NEGATIVE TEST] Update non-existent donor");
        boolean upd = service.updateDonor(9999, "8888888888", "NoCity");
        System.out.println("Update result for non-existent donor (expect false): " + upd);

        System.out.println("\n❌ [NEGATIVE TEST] Delete same donor twice");
        boolean del1 = service.deleteDonor(1);
        System.out.println("Delete donor ID=1 first time: " + del1);
        boolean del2 = service.deleteDonor(1);
        System.out.println("Delete donor ID=1 second time (expect false): " + del2);

        System.out.println("\n=== Manual Tests Completed ===");
    }
}
