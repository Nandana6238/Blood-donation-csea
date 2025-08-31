package com.bdms.test;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import java.time.LocalDate;

public class TestDonorDAO {
    public static void main(String[] args) {
        DonorDAO dao = new DonorDAO();

        // 1️⃣ Test Add Donor
        Donor d = new Donor();
        d.setName("Nisha");
        d.setAge(22);
        d.setGender("Female");
        d.setBloodGroup("O+");
        d.setPhone("9876543210");
        d.setCity("Kochi");
        d.setLastDonationDate(LocalDate.now());

        dao.addDonor(d);
        System.out.println("✅ Donor added!");

        // 2️⃣ Test List Donors
        System.out.println("\n---- Donor List ----");
        dao.getAllDonors().forEach(donor ->
            System.out.println(donor.getId() + " | " + donor.getName() + " | " + donor.getPhone() + " | " + donor.getCity())
        );

        // 3️⃣ Test Update Donor (⚠️ replace ID with one from your DB)
        int updateId = 1;  // Change this to an actual donor ID in your DB
        dao.updateDonor(updateId, "9999999999", "Thrissur");
        System.out.println("\n✅ Donor with ID " + updateId + " updated!");

        // Confirm update
        System.out.println("\n---- After Update ----");
        dao.getAllDonors().forEach(donor ->
            System.out.println(donor.getId() + " | " + donor.getName() + " | " + donor.getPhone() + " | " + donor.getCity())
        );

        // 4️⃣ Test Delete Donor (⚠️ replace ID with one from your DB)
        int deleteId = 2;  // Change this to an actual donor ID in your DB
        dao.deleteDonor(deleteId);
        System.out.println("\n✅ Donor with ID " + deleteId + " deleted!");

        // Confirm delete
        System.out.println("\n---- After Delete ----");
        dao.getAllDonors().forEach(donor ->
            System.out.println(donor.getId() + " | " + donor.getName() + " | " + donor.getPhone() + " | " + donor.getCity())
        );
    }
}
