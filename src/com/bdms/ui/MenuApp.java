package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuApp {

    private static final String[] VALID_BLOOD_GROUPS = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Blood Donor Management System ===");
        System.out.println("Choose mode: 1 = Mock Mode (offline), 2 = Database Mode (MySQL)");
        int choice = readInt(sc, "Enter mode (1 or 2): ");
        boolean mockMode = (choice == 1);

        DonorService service = new DonorService(mockMode);

        while (true) {
            System.out.println("\n=== MENU (" + (mockMode ? "MOCK" : "DB") + ") ===");
            System.out.println("1. Add Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. Search Donors");
            System.out.println("4. Update Donor");
            System.out.println("5. Delete Donor");
            System.out.println("6. Find Donor by Phone");
            System.out.println("7. Exit");
            int option = readInt(sc, "Enter choice: ");

            switch (option) {
                case 1 -> addDonorFlow(sc, service);
                case 2 -> viewAllDonors(service);
                case 3 -> searchDonorsFlow(sc, service);
                case 4 -> updateDonorFlow(sc, service);
                case 5 -> deleteDonorFlow(sc, service);
                case 6 -> findByPhoneFlow(sc, service);
                case 7 -> {
                    System.out.println("👋 Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("❌ Invalid choice, please try again.");
            }
        }
    }

    // ---------------- Flows ----------------

    private static void addDonorFlow(Scanner sc, DonorService service) {
        System.out.println("--- Add Donor ---");
        String name = readString(sc, "Enter Name: ");
        int age = readInt(sc, "Enter Age: ");
        if (age < 18) {
            System.out.println("❌ Donor must be at least 18 years old.");
            return;
        }
        String gender = readString(sc, "Enter Gender (M/F): ");

        String bloodGroup = readString(sc, "Enter Blood Group: ").toUpperCase();
        if (!isValidBloodGroup(bloodGroup)) {
            System.out.println("❌ Invalid blood group.");
            return;
        }

        String phone = readString(sc, "Enter Phone (10 digits): ");
        if (!isValidPhone(phone)) {
            System.out.println("❌ Invalid phone number.");
            return;
        }

        String city = readString(sc, "Enter City: ");

        String dateStr = readString(sc, "Enter Last Donation Date (YYYY-MM-DD or blank): ");
        LocalDate lastDonationDate = null;
        if (!dateStr.isBlank()) {
            try {
                lastDonationDate = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format.");
                return;
            }
        }

        Donor existing = service.findByPhone(phone);
        if (existing != null) {
            System.out.println("⚠️ Phone number already exists! Donor not added.");
        } else {
            Donor donor = new Donor(0, name, age, gender, bloodGroup, phone, city, lastDonationDate);
            boolean added = service.addDonor(donor);
            if (added) {
                System.out.println("✅ Donor added successfully!");
            } else {
                System.out.println("❌ Donor not added. Please check details.");
            }
        }
    }

    private static void viewAllDonors(DonorService service) {
        System.out.println("--- All Donors ---");
        List<Donor> allDonors = service.getAllDonors();
        if (allDonors.isEmpty()) {
            System.out.println("No donors found.");
        } else {
            allDonors.forEach(System.out::println);
        }
    }

    private static void searchDonorsFlow(Scanner sc, DonorService service) {
        System.out.println("--- Search Donors ---");
        String bg = readString(sc, "Enter Blood Group: ").toUpperCase();
        String city = readString(sc, "Enter City: ");
        List<Donor> found = service.searchDonors(bg, city);
        if (found.isEmpty()) {
            System.out.println("No matching donors found.");
        } else {
            found.forEach(System.out::println);
        }
    }

    private static void updateDonorFlow(Scanner sc, DonorService service) {
        System.out.println("--- Update Donor ---");
        int uid = readInt(sc, "Enter Donor ID: ");
        String newPhone = readString(sc, "Enter New Phone (10 digits): ");
        if (!isValidPhone(newPhone)) {
            System.out.println("❌ Invalid phone number.");
            return;
        }
        String newCity = readString(sc, "Enter New City: ");
        boolean updated = service.updateDonor(uid, newPhone, newCity);
        System.out.println(updated ? "✅ Donor updated." : "❌ Donor not found.");
    }

    private static void deleteDonorFlow(Scanner sc, DonorService service) {
        System.out.println("--- Delete Donor ---");
        int did = readInt(sc, "Enter Donor ID: ");
        boolean deleted = service.deleteDonor(did);
        System.out.println(deleted ? "✅ Donor deleted." : "❌ Donor not found.");
    }

    private static void findByPhoneFlow(Scanner sc, DonorService service) {
        System.out.println("--- Find Donor by Phone ---");
        String searchPhone = readString(sc, "Enter Phone Number: ");
        Donor d = service.findByPhone(searchPhone);
        if (d != null) {
            System.out.println("✅ Donor found: " + d);
        } else {
            System.out.println("❌ No donor found with this phone.");
        }
    }

    // ---------------- Helpers ----------------

    private static boolean isValidBloodGroup(String bg) {
        for (String b : VALID_BLOOD_GROUPS) {
            if (b.equalsIgnoreCase(bg))
                return true;
        }
        return false;
    }

    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private static String readString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
