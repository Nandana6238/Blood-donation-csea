

package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;
import java.time.LocalDate;
import java.util.Scanner;
import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import java.util.List;

public class MainApp {
    private static Scanner sc = new Scanner(System.in);
    private static DonorService donorService = new DonorService();

    public static void main(String[] args) {
        System.out.println("=== Blood Donor Management System ===");

        while (true) {
            showMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": // Add Donor 
                  try { 
                    System.out.print("Enter Name: "); 
                    String name = sc.nextLine().trim(); 
                    if (name.isEmpty()) { 
                    System.out.println("⚠ Name cannot be empty."); 
                    break; 
                   } 
                   System.out.print("Enter Age: "); 
                   int age = Integer.parseInt(sc.nextLine().trim()); 
                   if (age < 18 || age > 65) { 
                      System.out.println("⚠ Age must be between 18 and 65."); 
                    break; 
                   } 
                   System.out.print("Enter Blood Group (e.g., A+, O-, B+): "); 
                   String bloodGroup = sc.nextLine().toUpperCase().trim(); 
                   System.out.print("Enter Phone: "); 
                   String phone = sc.nextLine().trim(); 
                   Donor d = new Donor(name, age, bloodGroup, phone, null); 
                   donorService.addDonor(d); 
                   System.out.println(" Donor registered successfully!"); 
                 }catch (NumberFormatException e) { 
                   System.out.println("⚠ Invalid input format, please try again."); 
                } 
                break; 
                case "2":
                    searchDonorUI();
                    break;
                case "3":
                    viewAllDonorsUI();
                    break;
                case "4": // Update Donor 
                  try { 
                    System.out.print("Enter Donor ID to update: "); 
                    int uid = Integer.parseInt(sc.nextLine()); 
 
                    System.out.print("Enter new Phone: "); 
                    String newPhone = sc.nextLine(); 
 
                    if (!newPhone.matches("\\d{10}")) { 
                       System.out.println(" Invalid phone number! Must be 10 digits."); 
                    } else { 
                       donorService.updateDonorPhone(uid, newPhone); 
                       System.out.println("☎ Donor phone updated successfully!"); 
                    } 
                  }catch (NumberFormatException e) { 
                     System.out.println("⚠ Invalid input! Please enter a number."); 
                  } 
                  break;
                case "5":// Delete Donor 
                  try { 
                    System.out.print("Enter Donor ID to delete: "); 
                    int did = Integer.parseInt(sc.nextLine()); 
 
                    donorService.deleteDonor(did); 
                    System.out.println("🗑 Donor deleted successfully!"); 
                  } catch (NumberFormatException e) { 
                      System.out.println("⚠ Invalid input! Please enter a valid number."); 
                  } 
                  break; 
                case "6": // Submit Recipient Request 
                    System.out.print("Enter Recipient Name: "); 
                    String rName = sc.nextLine(); 
 
                    System.out.print("Enter Required Blood Group: "); 
                    String rBlood = sc.nextLine(); 
 
                    System.out.print("Enter City: "); 
                    String rCity = sc.nextLine(); 
 
                    Request req = new Request(rName, rBlood, rCity, LocalDate.now()); 
                    requestService.addRequest(req); 
                    System.out.println(" Request submitted successfully!"); 
                    break;
                case "7": // Search Donors by Blood Group + City 
                    System.out.print("Enter Blood Group: "); 
                    String bgroup = sc.nextLine(); 
                    System.out.print("Enter City: "); 
                    String city = sc.nextLine(); 
 
                    donorService.searchByBloodAndCity(bgroup, city).forEach(d -> { 
                    System.out.println(d); 
                    }); 
                    break;
                case "8": // View Recipient Request History 
                    requestService.getAllRequests().forEach(r -> { 
                    System.out.println(r); 
                    }); 
                    break;
                case "9": // Record a Donation 
                    System.out.print("Enter Donor ID: "); 
                    int dId = sc.nextInt(); sc.nextLine(); 
                    System.out.print("Enter Donation Date (YYYY-MM-DD): "); 
                    String dateStr = sc.nextLine(); 
                    LocalDate dDate = LocalDate.parse(dateStr); 
                    System.out.print("Enter Volume (ml): "); 
                    int volume = sc.nextInt(); sc.nextLine(); 
 
                    donationService.addDonation(new Donation(dId, dDate, volume)); 
                    System.out.println(" Donation recorded successfully!"); 
                    break;
                case "10": // View Donation History 
                    System.out.print("Enter Donor ID: "); 
                    int donorId = sc.nextInt(); sc.nextLine(); 
                    donationService.getDonationHistory(donorId).forEach(System.out::println); 
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
    private static void donorTestApp(){
        system.out.println("\n===Donor Test App===");
        List<Donor>donors=donorService.getAllDonor();
        for (Donor d:donors){
            System.out.println(d);
        }
     }
}