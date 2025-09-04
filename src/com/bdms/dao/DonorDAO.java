package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.util.DBConnection;

import java.sql.*;
<<<<<<< HEAD
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

public class DonorDAO {

    private static final List<Donor> mockDonors = new ArrayList<>();
    private boolean useMock = true; // 🔹 Change this: true = mock mode, false = DB mode

    static {
        // Mock data for testing (Week 1)
        mockDonors.add(new Donor("Aisha Khan", 24, "F", "A+", "9876543210", "Delhi", LocalDate.of(2025, 5, 1)));
        mockDonors.add(new Donor("Rahul Nair", 29, "M", "O+", "9876501234", "Kochi", LocalDate.of(2025, 7, 10)));
        mockDonors.add(new Donor("Devika P", 32, "F", "A+", "9998887776", "Delhi", null));
        mockDonors.add(new Donor("Arun Kumar", 41, "M", "B-", "9123456789", "Chennai", LocalDate.of(2025, 8, 10)));
    }

    // 🔹 Search donors (works in both modes)
    public List<Donor> searchDonors(String bloodGroup, String city) {
        if (useMock) {
            // Mock mode
            return mockDonors.stream()
                    .filter(d -> d.getBloodGroup().equalsIgnoreCase(bloodGroup)
                            && d.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        } else {
            // DB mode
            List<Donor> donors = new ArrayList<>();
            String sql = "SELECT * FROM donors WHERE blood_group=? AND city=?";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, bloodGroup);
                ps.setString(2, city);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Donor donor = new Donor();
                        donor.setId(rs.getInt("id"));
                        donor.setName(rs.getString("name"));
                        donor.setAge(rs.getInt("age"));
                        donor.setGender(rs.getString("gender"));
                        donor.setBloodGroup(rs.getString("blood_group"));
                        donor.setPhone(rs.getString("phone"));
                        donor.setCity(rs.getString("city"));
                        donor.setLastDonationDate(rs.getDate("last_donation_date") != null
                                ? rs.getDate("last_donation_date").toLocalDate()
                                : null);

                        donors.add(donor);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return donors;
        }
    }

    // ✅ DB-only methods (ignored in mock mode)
    public void addDonor(Donor donor) {
        if (useMock) {
            mockDonors.add(donor);
            return;
        }
        String sql = "INSERT INTO donors(name, age, gender, blood_group, phone, city, last_donation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, donor.getName());
            ps.setInt(2, donor.getAge());
            ps.setString(3, donor.getGender());
            ps.setString(4, donor.getBloodGroup());
            ps.setString(5, donor.getPhone());
            ps.setString(6, donor.getCity());
            if (donor.getLastDonationDate() != null) {
                ps.setDate(7, Date.valueOf(donor.getLastDonationDate()));
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

=======
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {
>>>>>>> origin/main
    public List<Donor> getAllDonors() {
        if (useMock) {
            return new ArrayList<>(mockDonors);
        }
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors";

        try (Connection conn = DBConnection.getConnection();
<<<<<<< HEAD
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
=======
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
>>>>>>> origin/main

            while (rs.next()) {
                Donor donor = new Donor();
                donor.setId(rs.getInt("id"));
                donor.setName(rs.getString("name"));
                donor.setAge(rs.getInt("age"));
                donor.setGender(rs.getString("gender"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setPhone(rs.getString("phone"));
                donor.setCity(rs.getString("city"));
<<<<<<< HEAD
                donor.setLastDonationDate(rs.getDate("last_donation_date") != null
                        ? rs.getDate("last_donation_date").toLocalDate()
                        : null);

=======
                donor.setLastDonationDate(
                    rs.getDate("last_donation_date") != null ?
                    rs.getDate("last_donation_date").toLocalDate() : null
                );
>>>>>>> origin/main
                donors.add(donor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }
<<<<<<< HEAD

    public boolean updateDonor(int id, String phone, String city) {
        if (useMock) {
            for (Donor d : mockDonors) {
                if (d.getId() == id) {
                    d.setPhone(phone);
                    d.setCity(city);
                    return true;
                }
            }
            return false;
        }
        String sql = "UPDATE donors SET phone=?, city=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ps.setString(2, city);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDonor(int id) {
        if (useMock) {
            return mockDonors.removeIf(d -> d.getId() == id);
        }
        String sql = "DELETE FROM donors WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
=======
}
>>>>>>> origin/main
