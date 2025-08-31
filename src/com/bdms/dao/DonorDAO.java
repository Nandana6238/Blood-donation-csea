package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.util.DBConnection;
import java.sql.*;
import java.sql.Date; 
import java.util.*;

public class DonorDAO {

    // 1. Add Donor
    public void addDonor(Donor donor) {
        String sql = "INSERT INTO donors(name, age, gender, blood_group, phone, city, last_donation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, donor.getName());
            ps.setInt(2, donor.getAge());
            ps.setString(3, donor.getGender());
            ps.setString(4, donor.getBloodGroup());
            ps.setString(5, donor.getPhone());
            ps.setString(6, donor.getCity());
           // ps.setDate(7, donor.getLastDonationDate() != null ? Date.valueOf(donor.getLastDonationDate()) : null);
           // ...existing code...
            if (donor.getLastDonationDate() != null) {
                ps.setDate(7, Date.valueOf(donor.getLastDonationDate()));
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
// ...existing code...
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Get all donors
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
                donor.setLastDonationDate(rs.getDate("last_donation_date") != null ? rs.getDate("last_donation_date").toLocalDate() : null);

                donors.add(donor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    // 3. Update donor
    public boolean updateDonor(int id, String phone, String city) {
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

// 4. Delete donor
public boolean deleteDonor(int id) {
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
