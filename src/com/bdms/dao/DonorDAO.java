package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.util.DBConnection;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DonorDAO {
    private static final List<Donor> mockDonors = new ArrayList<>();
    private boolean useMock = false; // default: use DB
    private static int nextId = 1;

    public DonorDAO() {}
    public DonorDAO(boolean useMock) {
        this.useMock = useMock;
    }

    public void setUseMock(boolean useMock) {
        this.useMock = useMock;
    }

    // Preload some mock donors
    static {
        mockDonors.add(new Donor("Aisha Khan", 24, "F", "A+", "9876543210", "Delhi", LocalDate.of(2025, 5, 1)));
        mockDonors.add(new Donor("Rahul Nair", 29, "M", "O+", "9876501234", "Kochi", LocalDate.of(2025, 7, 10)));
        mockDonors.add(new Donor("Devika P", 32, "F", "A+", "9998887776", "Delhi", null));
        mockDonors.add(new Donor("Arun Kumar", 41, "M", "B-", "9123456789", "Chennai", LocalDate.of(2025, 8, 10)));

        int idCounter = 1;
        for (Donor d : mockDonors) {
            d.setId(idCounter++);
        }
        nextId = idCounter;
    }

    // 🔍 Search donors
    public List<Donor> searchDonors(String bloodGroup, String city) {
        if (useMock) {
            return mockDonors.stream()
                    .filter(d -> d.getBloodGroup().equalsIgnoreCase(bloodGroup)
                            && d.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        } else {
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

    // ➕ Add donor
    public void addDonor(Donor donor) {
        if (useMock) {
            donor.setId(nextId++);
            mockDonors.add(donor);
            return;
        }

        String sql = "INSERT INTO donors(name, age, gender, blood_group, phone, city, last_donation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("⚠ Transaction failed (addDonor). Rolled back. " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error adding donor: " + e.getMessage());
        }
    }

    // 📋 Get all donors
    public List<Donor> getAllDonors() {
        if (useMock)
            return new ArrayList<>(mockDonors);

        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    // ✏️ Update donor
    public boolean updateDonor(Donor donor) {
        if (useMock) {
            for (Donor d : mockDonors) {
                if (d.getId() == donor.getId()) {
                    d.setName(donor.getName());
                    d.setAge(donor.getAge());
                    d.setGender(donor.getGender());
                    d.setBloodGroup(donor.getBloodGroup());
                    d.setPhone(donor.getPhone());
                    d.setCity(donor.getCity());
                    d.setLastDonationDate(donor.getLastDonationDate());
                    return true;
                }
            }
            return false;
        }

        String sql = "UPDATE donors SET name=?, age=?, gender=?, blood_group=?, phone=?, city=?, last_donation_date=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
                ps.setInt(8, donor.getId());

                int rows = ps.executeUpdate();
                conn.commit();
                return rows > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("⚠ Transaction failed (updateDonor). Rolled back. " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error updating donor: " + e.getMessage());
            return false;
        }
    }

    // ❌ Delete donor
    public boolean deleteDonor(int id) {
        if (useMock) {
            return mockDonors.removeIf(d -> d.getId() == id);
        }

        String sql = "DELETE FROM donors WHERE id=?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                conn.commit();
                return rows > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("⚠ Transaction failed (deleteDonor). Rolled back. " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error deleting donor: " + e.getMessage());
            return false;
        }
    }
}
