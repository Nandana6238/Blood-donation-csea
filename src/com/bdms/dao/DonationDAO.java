package com.bdms.dao;

import com.bdms.model.Donation;
import com.bdms.util.DBConnection;
import java.sql.*;
import java.util.*;

public class DonationDAO {

    // ✅ Add new donation (with transaction + rollback)
    public void addDonation(Donation donation) {
        String sql = "INSERT INTO donations(donor_id, donation_date, volume_ml) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, donation.getDonorId());
                ps.setDate(2, java.sql.Date.valueOf(donation.getDonationDate()));
                ps.setInt(3, donation.getVolumeMl());

                ps.executeUpdate();
                conn.commit(); // ✅ commit if success
            } catch (SQLException e) {
                conn.rollback(); // ❌ rollback on error
                System.out.println("⚠ Transaction failed (addDonation). Rolled back. " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // restore default
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error adding donation: " + e.getMessage());
        }
    }

    // ✅ Get donation history for a donor
    public List<Donation> getDonationHistory(int donorId) {
        List<Donation> list = new ArrayList<>();
        String sql = "SELECT * FROM donations WHERE donor_id = ? ORDER BY donation_date DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, donorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Donation d = new Donation(
                        rs.getInt("id"),
                        rs.getInt("donor_id"),
                        rs.getDate("donation_date").toLocalDate(),
                        rs.getInt("volume_ml"));
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error retrieving donation history: " + e.getMessage());
        }
        return list;
    }

    // ✅ Get all donations (admin view)
    public List<Donation> getAllDonations() {
        List<Donation> list = new ArrayList<>();
        String sql = "SELECT * FROM donations ORDER BY donation_date DESC";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Donation d = new Donation(
                        rs.getInt("id"),
                        rs.getInt("donor_id"),
                        rs.getDate("donation_date").toLocalDate(),
                        rs.getInt("volume_ml"));
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error retrieving all donations: " + e.getMessage());
        }
        return list;
    }

    // (Optional) ✅ Delete donation (with transaction)
    public boolean deleteDonation(int donationId) {
        String sql = "DELETE FROM donations WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, donationId);
                int rows = ps.executeUpdate();
                conn.commit();
                return rows > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("⚠ Transaction failed (deleteDonation). Rolled back. " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error deleting donation: " + e.getMessage());
            return false;
        }
    }
}
