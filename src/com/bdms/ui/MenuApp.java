package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DonorService service = new DonorService(true); // Force MOCK mode only

        while (true) {
            System.out.println("\n--- BDMS Menu (MOCK mode only) ---");
            System.out.println("1. Add donor");
            System.out.println("2. List donors");
            System.out.println("3. Search donors (blood+city)");
            System.out.println("4. Update donor (id)");
            System.out.println("5. Delete donor (id)");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                continue;
            }

            switch (ch) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = Integer.parseInt(sc.nextLine());
                    System.out.print("Gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Blood group: ");
                    String bg = sc.nextLine();
                    System.out.print("Phone: ");
                    String phone = sc.nextLine();
                    System.out.print("City: ");
                    String city = sc.nextLine();
                    Donor d = new Donor(name, age, gender, bg, phone, city, LocalDate.now());
                    service.addDonor(d);
                    System.out.println("Added successfully.");
                }
                case 2 -> {
                    List<Donor> all = service.getAllDonors();
                    if (all.isEmpty()) {
                        System.out.println("No donors available.");
                    } else {
                        all.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("Blood group: ");
                    String bg = sc.nextLine();
                    System.out.print("City: ");
                    String city = sc.nextLine();
                    List<Donor> results = service.searchDonors(bg, city);
                    if (results.isEmpty()) {
                        System.out.println("No matching donors found.");
                    } else {
                        results.forEach(System.out::println);
                    }
                }
                case 4 -> {
                    System.out.print("Donor id to update: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("New phone: ");
                    String phone = sc.nextLine();
                    System.out.print("New city: ");
                    String city = sc.nextLine();
                    boolean ok = service.updateDonor(id, phone, city);
                    System.out.println("Update result: " + ok);
                }
                case 5 -> {
                    System.out.print("Donor id to delete: ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean ok = service.deleteDonor(id);
                    System.out.println("Delete result: " + ok);
                }
                case 6 -> {
                    sc.close();
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
