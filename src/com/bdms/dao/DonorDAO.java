package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.ui.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {
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