package com.bdms.ui;

import com.bdms.service.DonorService;

public class TestSearch {
    public static void main(String[] args) {
        DonorService service = new DonorService();

        System.out.println("=== Searching A+ in Delhi ===");
        service.search("A+", "Delhi").forEach(System.out::println);

        System.out.println("\n=== Searching O+ in Kochi ===");
        service.search("O+", "Kochi").forEach(System.out::println);

        System.out.println("\n=== Searching B- in Delhi ===");
        service.search("B-", "Delhi").forEach(System.out::println);
    }
}
