package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {
    // ✅ Add Donor (with transaction + rollback)
    public void addDonor(Donor donor) {
        String sql = "INSERT INTO donors(name, age, gender, blood_group, phone, city, last_donation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

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
                conn.commit(); // ✅ Commit changes
            } catch (SQLException e) {
                conn.rollback(); // ❌ Rollback on failure
                System.out.println("⚠ Transaction failed (addDonor). Rolled back. " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // Restore default
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error adding donor: " + e.getMessage());
        }
    }

    // ✅ Update Donor (with transaction + rollback)
    public boolean updateDonor(Donor donor) {
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

    // ✅ Delete Donor (with transaction + rollback)
    public boolean deleteDonor(int id) {
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
    public List<Donor> getAllDonors() {
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
                donor.setLastDonationDate(
                    rs.getDate("last_donation_date") != null ?
                    rs.getDate("last_donation_date").toLocalDate() : null
                );
                donors.add(donor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }
}