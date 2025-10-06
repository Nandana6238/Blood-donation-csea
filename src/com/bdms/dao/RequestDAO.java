package com.bdms.dao;

import com.bdms.model.Request;
import com.bdms.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {

    // 1. Add Request
    public void addRequest(Request req) {
        String sql = "INSERT INTO requests(recipient_name, blood_group, city, request_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, req.getRecipientName());
            ps.setString(2, req.getBloodGroup());
            ps.setString(3, req.getCity());
            ps.setDate(4, Date.valueOf(req.getRequestDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("⚠ Error inserting request: " + e.getMessage());
        }
    }

    // 2. Get all requests
    public List<Request> getAllRequests() {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM requests ORDER BY request_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Request r = new Request(
                        rs.getInt("id"),
                        rs.getString("recipient_name"),
                        rs.getString("blood_group"),
                        rs.getString("city"),
                        rs.getDate("request_date").toLocalDate()
                );
                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error retrieving requests: " + e.getMessage());
        }
        return list;
    }

    // 3. Update request (update city + date by id)
    public boolean updateRequest(int id, String city, LocalDate newDate) {
        String sql = "UPDATE requests SET city=?, request_date=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);
            ps.setDate(2, Date.valueOf(newDate));
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("⚠ Error updating request: " + e.getMessage());
            return false;
        }
    }

    // 4. Delete request
    public boolean deleteRequest(int id) {
        String sql = "DELETE FROM requests WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("⚠ Error deleting request: " + e.getMessage());
            return false;
        }
    }
}
