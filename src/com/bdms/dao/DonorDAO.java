package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DonorDAO {
    private static final List<Donor> mockDonors = new ArrayList<>();
    private boolean mockMode = true; // default mock

    // Constructor
    public DonorDAO(boolean mockMode) {
        this.mockMode = mockMode;

        if (mockMode && mockDonors.isEmpty()) {
            // preload sample donors
            mockDonors.add(new Donor(0, "Aisha Khan", 24, "F", "A+", "9876543210", "Delhi", LocalDate.of(2025, 5, 1)));
            mockDonors.add(new Donor(0, "Rahul Nair", 29, "M", "O+", "9876501234", "Kochi", LocalDate.of(2025, 7, 10)));
            mockDonors.add(new Donor(0, "Devika P", 32, "F", "A+", "9998887776", "Delhi", null));
            mockDonors
                    .add(new Donor(0, "Arun Kumar", 41, "M", "B-", "9123456789", "Chennai", LocalDate.of(2025, 8, 10)));
        }
    }

    // 1. Search Donors
    public List<Donor> searchDonors(String bloodGroup, String city) {
        if (mockMode) {
            return mockDonors.stream()
                    .filter(d -> d.getBloodGroup().equalsIgnoreCase(bloodGroup)
                            && d.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }

        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group=? AND city=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ps.setString(2, city);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Donor donor = mapResultSet(rs);
                donors.add(donor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    // 2. Add Donor
    public void addDonor(Donor donor) {
        if (mockMode) {
            donor.setId(mockDonors.size() + 1);
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
                ps.setNull(7, Types.DATE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Get All Donors
    public List<Donor> getAllDonors() {
        if (mockMode)
            return new ArrayList<>(mockDonors);

        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                donors.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    // 4. Update Donor
    public boolean updateDonor(int id, String phone, String city) {
        if (mockMode) {
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Delete Donor
    public boolean deleteDonor(int id) {
        if (mockMode) {
            return mockDonors.removeIf(d -> d.getId() == id);
        }

        String sql = "DELETE FROM donors WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper: map ResultSet -> Donor
    private Donor mapResultSet(ResultSet rs) throws SQLException {
        Donor donor = new Donor();
        donor.setId(rs.getInt("id"));
        donor.setName(rs.getString("name"));
        donor.setAge(rs.getInt("age"));
        donor.setGender(rs.getString("gender"));
        donor.setBloodGroup(rs.getString("blood_group"));
        donor.setPhone(rs.getString("phone"));
        donor.setCity(rs.getString("city"));
        donor.setLastDonationDate(
                rs.getDate("last_donation_date") != null
                        ? rs.getDate("last_donation_date").toLocalDate()
                        : null);
        return donor;
    }
}
