

package com.bdms.ui;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import com.bdms.service.DonorService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;



public class MainApp {
    private static Scanner sc = new Scanner(System.in);
    private static DonorService donorService = new DonorService();

    public static void main(String[] args) {
        System.out.println("=== Blood Donor Management System ===");

        while (true) {
            showMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addDonorUI();
                    break;
                case "2":
                    searchDonorUI();
                    break;
                case "3":
                    viewAllDonorsUI();
                    break;
                case "4":
                    updateDonorUI();
                    break;
                case "5":
                    deleteDonorUI();
                    break;
                case "6":
                    checkEligibilityUI();
                    break;
                case "9":
                    DonorDAO dao = new DonorDAO();
                    List<Donor> donors = dao.getAllDonors();
                    for (Donor d : donors) {
            System.out.println(d);
                    }
                    break;
                    
                case "0":
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // === Menu ===
    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Register Donor");
        System.out.println("2. Search Donor");
        System.out.println("3. List All Donors");
        System.out.println("4. Update Donor Contact");
        System.out.println("5. Delete Donor");
        System.out.println("6. Check Donor Eligibility");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    // === Week 2 Implementation ===
    private static void addDonorUI() {
        System.out.println("\n=== Register Donor ===");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Gender: ");
        String gender = sc.nextLine();

        System.out.print("Blood Group: ");
        String bloodGroup = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("City: ");
        String city = sc.nextLine();

        Donor donor = new Donor(name, age, gender, bloodGroup, phone, city, LocalDate.now());

        donorService.addDonor(donor);
        System.out.println("✅ Donor registered successfully!");
    }

    // === Stubs for teammates (they’ll implement later) ===
    private static void searchDonorUI() {
        System.out.println("[Stub] Search Donor selected");
        // Later: call donorService.searchDonors()
    }

    private static void viewAllDonorsUI() {
        System.out.println("[Stub] List All Donors selected");
        // Later: call donorService.listAllDonors()
    }

    private static void updateDonorUI() {
        System.out.println("[Stub] Update Donor selected");
        // Later: call donorService.updateDonor()
    }

    private static void deleteDonorUI() {
        System.out.println("[Stub] Delete Donor selected");
        // Later: call donorService.deleteDonor()
    }

    private static void checkEligibilityUI() {
        System.out.println("[Stub] Check Eligibility selected");
        // Later: call donorService.checkEligibility()
    }
}