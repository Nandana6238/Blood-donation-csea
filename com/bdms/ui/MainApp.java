
package com.bdms.ui; 
 
import java.util.Scanner; 
 
class MainApp { 
    private static Scanner sc = new Scanner(System.in); 
public static void main(String[] args) { 
        System.out.println("=== Blood Donor Management System ==="); 
        while (true) { 
            showMenu(); 
            String choice = sc.nextLine().trim(); 
 
            switch (choice) { 
                case "1": 
                    System.out.println("[Stub] Register Donor selected"); 
                    // To be implemented: call DonorService.registerDonor() 
                    break; 
                case "2": 
                    System.out.println("[Stub] Search Donor selected");
                 // To be implemented: call DonorService.searchDonors() 
                    break; 
                case "3": 
                    System.out.println("[Stub] List All Donors selected"); 
                    // To be implemented: call DonorService.listAllDonors() 
                    break; 
                case "4": 
                    System.out.println("[Stub] Update Donor selected"); 
                    // To be implemented: call DonorService.updateDonor() 
                    break; 
                case "5": 
                    System.out.println("[Stub] Delete Donor selected"); 
                    // To be implemented: call DonorService.deleteDonor() 
                    break; 
                case "6": 
                    System.out.println("[Stub] Check Eligibility selected"); 
                    // To be implemented: call DonorService.checkEligibility() 
                    break; 
                case "0": 
                    System.out.println("Exiting... Goodbye!"); 
                    System.exit(0); 
                    break; 
                default: 
                    System.out.println("Invalid choice! Please try again."); 
            } 
        } 
    } 
 
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
} 
