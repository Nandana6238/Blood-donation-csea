package com.bdms.test;

import com.bdms.dao.DonationDAO;
import com.bdms.model.Donation;

import java.time.LocalDate;
import java.util.List;

public class TestDonationDAO {
    public static void main(String[] args) {
        DonationDAO donationDAO = new DonationDAO();

        // 1. Add a valid donation for donor_id=1 (must exist in donors table)
        Donation d1 = new Donation(0, 1, LocalDate.now(), 450);
        donationDAO.addDonation(d1);
        System.out.println("✅ Valid donation inserted");

        // 2. Retrieve donation history for donor_id=1
        System.out.println("\n📜 Donation History for donor 1:");
        List<Donation> history = donationDAO.getDonationHistory(1);
        for (Donation d : history) {
            System.out.println(d);
        }

        // 3. Add an invalid donation (foreign key fails → rollback)
        Donation d2 = new Donation(0, 999, LocalDate.now(), 350); // donor 999 doesn't exist
        donationDAO.addDonation(d2);
        System.out.println("⚠️ Invalid donation attempted (should rollback)");

        // 4. (Optional) Delete test - only works if you add deleteDonation() in DonationDAO
        /*
        donationDAO.deleteDonation(1); // delete donation with id=1
        System.out.println("🗑 Deleted donation with ID 1");

        // recheck all donations
        System.out.println("\n📜 All Donations after delete:");
        for (Donation d : donationDAO.getAllDonations()) {
            System.out.println(d);
        }
        */
    }
}
