package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuApp {
    public static void main(String[] args) {
        boolean useMock = true; // flip to false for DB
        DonorService service = new DonorService(useMock);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== Blood Donor Management ===");
                System.out.println("1. Search donors");
                System.out.println("2. Add donor");
                System.out.println("3. View all donors");
                System.out.println("0. Exit");
                System.out.print("Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    } // Scanner closes automatically
                    case 1 -> {
                        System.out.print("Blood group: ");
                        String bg = sc.nextLine();
                        System.out.print("City: ");
                        String city = sc.nextLine();
                        service.searchDonors(bg, city).forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Gender: ");
                        String gender = sc.nextLine();
                        System.out.print("Blood Group: ");
                        String bg = sc.nextLine();
                        System.out.print("Phone: ");
                        String phone = sc.nextLine();
                        System.out.print("City: ");
                        String city = sc.nextLine();
                        Donor donor = new Donor(name, age, gender, bg, phone, city, LocalDate.now());
                        service.addDonor(donor);
                        System.out.println("Donor added.");
                    }
                    case 3 -> service.getAllDonors().forEach(System.out::println);
                    case 4 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            }
        }
    }
}
