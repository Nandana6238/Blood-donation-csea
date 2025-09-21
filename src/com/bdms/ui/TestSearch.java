package com.bdms.ui;

import com.bdms.service.DonorService;

public class TestSearch {
    public static void main(String[] args) {
        // true = Mock Mode, false = Database Mode
        DonorService service = new DonorService(true);

        System.out.println("=== Searching A+ in Delhi ===");
        service.searchDonors("A+", "Delhi").forEach(System.out::println);

        System.out.println("\n=== Searching O+ in Kochi ===");
        service.searchDonors("O+", "Kochi").forEach(System.out::println);

        System.out.println("\n=== Searching B- in Delhi ===");
        service.searchDonors("B-", "Delhi").forEach(System.out::println);
    }
}
