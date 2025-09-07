package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Blood Donor Management System ===");
        System.out.println("Choose mode: 1 = Mock Mode (offline), 2 = Database Mode (MySQL)");
        int choice = sc.nextInt();
        boolean mockMode = (choice == 1);

        DonorService service = new DonorService(mockMode);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Add Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. Search Donors");
            System.out.println("4. Update Donor");
            System.out.println("5. Delete Donor");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int option = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (option) {
                case 1: // Add Donor
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Enter Blood Group: ");
                    String bloodGroup = sc.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter City: ");
                    String city = sc.nextLine();
                    System.out.print("Enter Last Donation Date (YYYY-MM-DD or blank): ");
                    String dateStr = sc.nextLine();
                    LocalDate lastDonationDate = dateStr.isBlank() ? null : LocalDate.parse(dateStr);

                    Donor donor = new Donor(0, name, age, gender, bloodGroup, phone, city, lastDonationDate);
                    service.addDonor(donor);
                    System.out.println("✅ Donor added successfully!");
                    break;

                case 2: // View All Donors
                    List<Donor> allDonors = service.getAllDonors();
                    if (allDonors.isEmpty()) {
                        System.out.println("No donors found.");
                    } else {
                        allDonors.forEach(System.out::println);
                    }
                    break;

                case 3: // Search Donors
                    System.out.print("Enter Blood Group: ");
                    String bg = sc.nextLine();
                    System.out.print("Enter City: ");
                    String cty = sc.nextLine();
                    List<Donor> found = service.searchDonors(bg, cty);
                    if (found.isEmpty()) {
                        System.out.println("No matching donors found.");
                    } else {
                        found.forEach(System.out::println);
                    }
                    break;

                case 4: // Update Donor
                    System.out.print("Enter Donor ID: ");
                    int uid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Phone: ");
                    String newPhone = sc.nextLine();
                    System.out.print("Enter New City: ");
                    String newCity = sc.nextLine();
                    boolean updated = service.updateDonor(uid, newPhone, newCity);
                    System.out.println(updated ? "✅ Donor updated." : "❌ Donor not found.");
                    break;

                case 5: // Delete Donor
                    System.out.print("Enter Donor ID: ");
                    int did = sc.nextInt();
                    boolean deleted = service.deleteDonor(did);
                    System.out.println(deleted ? "✅ Donor deleted." : "❌ Donor not found.");
                    break;

                case 6: // Exit
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
