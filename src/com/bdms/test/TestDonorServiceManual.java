package com.bdms.test;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;

public class TestDonorServiceManual {
    public static void main(String[] args) {
        boolean useMock = true; // flip for DB
        DonorService service = new DonorService(useMock);

        System.out.println("[TEST] Search A+ Delhi");
        System.out.println(service.searchDonors("A+", "Delhi"));

        System.out.println("[TEST] Add donor");
        service.addDonor(new Donor("Test User", 25, "F", "AB+", "99999", "Delhi", LocalDate.now()));
        System.out.println(service.searchDonors("AB+", "Delhi"));

        System.out.println("[TEST] Get all donors");
        service.getAllDonors().forEach(System.out::println);
    }
}
