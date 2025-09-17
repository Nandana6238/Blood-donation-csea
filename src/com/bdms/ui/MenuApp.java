package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

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
            System.out.println("3. Search Donors (BloodGroup + City)");
            System.out.println("4. Update Donor");
            System.out.println("5. Delete Donor");
            System.out.println("6. Find Donor by Phone");
            System.out.println("7. Reports & CSV Export");
            System.out.println("8. Exit");
            int option = readInt(sc, "Enter choice: ");

            switch (option) {
                case 1 -> addDonorFlow(sc, service);
                case 2 -> viewAllDonors(service);
                case 3 -> searchDonorsFlow(sc, service);
                case 4 -> updateDonorFlow(sc, service);
                case 5 -> deleteDonorFlow(sc, service);
                case 6 -> findByPhoneFlow(sc, service);
                case 7 -> reportsFlow(sc, service);
                case 8 -> {
                    System.out.println("👋 Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("❌ Invalid choice, please try again.");
            }
        }
    }

    // ---------------- Reports Flow ----------------

    private static void reportsFlow(Scanner sc, DonorService service) {
        List<Donor> lastReport = null;
        while (true) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Donors by City (detail)");
            System.out.println("2. Donors by Blood Group (detail)");
            System.out.println("3. Eligible Donors (detail)");
            System.out.println("4. Summary: Count by Blood Group");
            System.out.println("5. Summary: Count by City & Blood Group");
            System.out.println("6. Export last detail report to CSV");
            System.out.println("7. Export summary to CSV");
            System.out.println("8. Back");
            int r = readInt(sc, "Choose report: ");

            switch (r) {
                case 1 -> {
                    String city = readString(sc, "Enter City: ");
                    lastReport = service.getDonorsByCity(city);
                    printDonorTable(lastReport);
                }
                case 2 -> {
                    String bg = readString(sc, "Enter Blood Group: ").toUpperCase();
                    if (!isValidBloodGroup(bg)) {
                        System.out.println("❌ Invalid blood group.");
                        break;
                    }
                    lastReport = service.getDonorsByBloodGroup(bg);
                    printDonorTable(lastReport);
                }
                case 3 -> {
                    int months = readInt(sc, "Enter minimum months since last donation: ");
                    lastReport = service.getEligibleDonors(months);
                    printDonorTable(lastReport);
                }
                case 4 -> {
                    Map<String, Integer> counts = service.countDonorsByBloodGroup();
                    printSummaryMap(counts, "Count by Blood Group");
                }
                case 5 -> {
                    Map<String, Integer> counts = service.countDonorsByCityAndBloodGroup();
                    printCityBloodGroupSummary(counts);
                }
                case 6 -> {
                    if (lastReport == null || lastReport.isEmpty()) {
                        System.out.println("Run a detail report first (options 1-3) before exporting.");
                        break;
                    }
                    String filename = readString(sc, "Enter filename (e.g. donors_detail.csv): ");
                    boolean ok = writeCsv(lastReport, filename);
                    System.out.println(ok ? "✅ CSV written: exports/" + filename : "❌ Failed to write CSV.");
                }
                case 7 -> {
                    System.out.println("Which summary to export? 1=BloodGroup 2=City&BloodGroup");
                    int ex = readInt(sc, "Choose: ");
                    if (ex == 1) {
                        Map<String, Integer> map = service.countDonorsByBloodGroup();
                        String filename = readString(sc, "Enter filename (e.g. summary_bg.csv): ");
                        boolean ok = writeSummaryCsv(map, filename, "blood_group");
                        System.out.println(ok ? "✅ CSV written: exports/" + filename : "❌ Failed to write CSV.");
                    } else if (ex == 2) {
                        Map<String, Integer> map = service.countDonorsByCityAndBloodGroup();
                        String filename = readString(sc, "Enter filename (e.g. summary_city_bg.csv): ");
                        boolean ok = writeCityBloodGroupCsv(map, filename);
                        System.out.println(ok ? "✅ CSV written: exports/" + filename : "❌ Failed to write CSV.");
                    } else {
                        System.out.println("❌ Invalid choice.");
                    }
                }
                case 8 -> {
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
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
            System.out.println(added ? "✅ Donor added successfully!" : "❌ Donor not added.");
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

    private static void printDonorTable(List<Donor> donors) {
        if (donors == null || donors.isEmpty()) {
            System.out.println("No donors found for this report.");
            return;
        }
        System.out.printf("%-4s %-20s %-4s %-6s %-6s %-12s %-12s %-12s%n",
                "ID", "Name", "Age", "Gender", "BGroup", "Phone", "City", "LastDonation");
        System.out.println("--------------------------------------------------------------------------------");
        for (Donor d : donors) {
            String dateStr = d.getLastDonationDate() == null ? "N/A" : d.getLastDonationDate().toString();
            System.out.printf("%-4d %-20s %-4d %-6s %-6s %-12s %-12s %-12s%n",
                    d.getId(), d.getName(), d.getAge(), d.getGender(), d.getBloodGroup(),
                    d.getPhone(), d.getCity(), dateStr);
        }
    }

    private static void printSummaryMap(Map<String, Integer> map, String title) {
        System.out.println("--- " + title + " ---");
        if (map == null || map.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        System.out.printf("%-25s %8s%n", "KEY", "COUNT");
        System.out.println("-----------------------------------------");
        for (Entry<String, Integer> e : map.entrySet()) {
            String key = e.getKey().equals("UNKNOWN") ? "Not Specified" : e.getKey();
            System.out.printf("%-25s %8d%n", key, e.getValue());
        }
    }

    private static void printCityBloodGroupSummary(Map<String, Integer> map) {
        System.out.println("--- Count by City & Blood Group ---");
        if (map == null || map.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        System.out.printf("%-15s %-10s %8s%n", "CITY", "BGroup", "COUNT");
        System.out.println("---------------------------------------------");
        for (Entry<String, Integer> e : map.entrySet()) {
            String[] parts = e.getKey().split(" - ", 2);
            String city = parts.length > 0 ? parts[0] : "Not Specified";
            String bg = parts.length > 1 ? parts[1] : "Not Specified";
            System.out.printf("%-15s %-10s %8d%n", city, bg, e.getValue());
        }
    }

    // === CSV Writers with exports/ ===

    private static java.io.File ensureExportsDir() {
        java.io.File f = new java.io.File("exports");
        if (!f.exists())
            f.mkdir();
        return f;
    }

    private static boolean writeCsv(List<Donor> donors, String filename) {
        try {
            java.io.File f = ensureExportsDir();
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.File(f, filename));
            pw.println("id,name,age,gender,blood_group,phone,city,last_donation_date");
            for (Donor d : donors) {
                String date = d.getLastDonationDate() == null ? "" : d.getLastDonationDate().toString();
                String name = d.getName().replace(",", " ");
                String city = (d.getCity() == null ? "" : d.getCity().replace(",", " "));
                pw.printf("%d,%s,%d,%s,%s,%s,%s,%s%n",
                        d.getId(), name, d.getAge(), d.getGender(), d.getBloodGroup(),
                        d.getPhone(), city, date);
            }
            pw.close();
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean writeSummaryCsv(Map<String, Integer> map, String filename, String keyHeader) {
        try {
            java.io.File f = ensureExportsDir();
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.File(f, filename));
            pw.printf("%s,count%n", keyHeader);
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                String key = e.getKey().replace(",", " ");
                if (key.equals("UNKNOWN"))
                    key = "Not Specified";
                pw.printf("%s,%d%n", key, e.getValue());
            }
            pw.close();
            return true;
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static boolean writeCityBloodGroupCsv(Map<String, Integer> map, String filename) {
        try {
            java.io.File f = ensureExportsDir();
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.File(f, filename));
            pw.println("city,blood_group,count");
            for (Entry<String, Integer> e : map.entrySet()) {
                String[] parts = e.getKey().split(" - ", 2);
                String city = parts.length > 0 ? parts[0].replace(",", " ") : "Not Specified";
                String bg = parts.length > 1 ? parts[1] : "Not Specified";
                pw.printf("%s,%s,%d%n", city, bg, e.getValue());
            }
            pw.close();
            return true;
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // --- Validators & Readers ---

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
