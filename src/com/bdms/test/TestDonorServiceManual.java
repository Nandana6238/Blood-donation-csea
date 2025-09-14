package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.List;

public class TestDonorServiceManual {
    public static void main(String[] args) {
        boolean useMock = true; // 👉 flip to false for DB mode
        DonorService service = new DonorService(useMock);

        System.out.println("=== Running Manual Tests (" + (useMock ? "MOCK" : "DB") + ") ===\n");

        // 1) Search existing donors
        System.out.println("[TEST] Search A+ Delhi");
        List<Donor> result1 = service.searchDonors("A+", "Delhi");
        System.out.println(result1.isEmpty() ? "❌ None found" : result1);

        // 2) Add donor - new phone (should succeed)
        System.out.println("\n[TEST] Add new donor (unique phone)");
        Donor d1 = new Donor(0, "Test User", 25, "F", "AB+", "1234567890", "Delhi", LocalDate.now());
        boolean added1 = service.addDonor(d1);
        System.out.println("Add result: " + added1);

        // 3) Add donor - duplicate phone (should fail)
        System.out.println("\n[TEST] Add donor with duplicate phone");
        Donor d2 = new Donor(0, "Duplicate User", 30, "M", "O+", "1234567890", "Delhi", null);
        boolean added2 = service.addDonor(d2);
        System.out.println("Add result (duplicate expected false): " + added2);

        // 4) Update existing donor (valid ID)
        System.out.println("\n[TEST] Update donor (valid ID)");
        boolean upd1 = service.updateDonor(d1.getId(), "1111111111", "Mumbai");
        System.out.println("Update result (expected true): " + upd1);

        // 5) Update non-existing donor (invalid ID)
        System.out.println("\n[TEST] Update donor (invalid ID)");
        boolean upd2 = service.updateDonor(9999, "2222222222", "Nowhere");
        System.out.println("Update result (expected false): " + upd2);

        // 6) Delete donor (valid ID, first time)
        System.out.println("\n[TEST] Delete donor (valid ID)");
        boolean del1 = service.deleteDonor(d1.getId());
        System.out.println("Delete result (expected true): " + del1);

        // 7) Delete same donor again (should fail)
        System.out.println("\n[TEST] Delete donor again (already deleted)");
        boolean del2 = service.deleteDonor(d1.getId());
        System.out.println("Delete result (expected false): " + del2);

        // 8) Search donor that doesn’t exist
        System.out.println("\n[TEST] Search donors (blood group=ZZ, city=Nowhere)");
        List<Donor> result2 = service.searchDonors("ZZ", "Nowhere");
        System.out.println(result2.isEmpty() ? "✅ Correct, none found" : result2);

        // 9) View all donors (final list)
        System.out.println("\n[TEST] All donors (final state)");
        service.getAllDonors().forEach(System.out::println);
    }
}
