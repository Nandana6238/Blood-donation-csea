package com.bdms.dao;

import com.bdms.model.Donor;
import com.bdms.ui.DBConnection;
import com.bdms.util.DBConnection;
import com.bdms.util.ImportResult;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data Access Object (DAO) for Donor entity.
 * <p>
 * Supports two modes:
 * <ul>
 * <li><b>Mock Mode:</b> Uses in-memory donor list for testing without DB.</li>
 * <li><b>DB Mode:</b> Connects to database using {@link DBConnection} and
 * executes SQL queries.</li>
 * </ul>
 * <p>
 * Provides CRUD operations, search/filter methods, donor statistics,
 * and CSV import functionality (Week 7).
 */
public class DonorDAO {
    private static final List<Donor> mockDonors = new ArrayList<>();
    private final boolean mockMode;
    private static int mockIdCounter = 1;

    static {
        // initial mock data
        mockDonors.add(new Donor(0, "Aisha Khan", 24, "F", "A+", "9876543210", "Delhi", LocalDate.of(2025, 5, 1)));
        mockDonors.add(new Donor(0, "Rahul Nair", 29, "M", "O+", "9876501234", "Kochi", LocalDate.of(2025, 7, 10)));
        mockDonors.add(new Donor(0, "Devika P", 32, "F", "A+", "9998887776", "Delhi", null));
        mockDonors.add(new Donor(0, "Arun Kumar", 41, "M", "B-", "9123456789", "Chennai", LocalDate.of(2025, 8, 10)));
    }

    /**
     * Constructor to initialize DonorDAO.
     *
     * @param mockMode true if mock (in-memory) mode, false if DB mode
     */
    public DonorDAO(boolean mockMode) {
        this.mockMode = mockMode;
        if (mockMode) {
            for (Donor d : mockDonors) {
                if (d.getId() == 0) {
                    d.setId(mockIdCounter++);
                }
            }
        }
    }

    /**
     * Adds a new donor.
     *
     * @param donor Donor object to add
     * @return true if added successfully, false if duplicate phone or DB error
     */
    public boolean addDonor(Donor donor) {
        if (mockMode) {
            if (existsByPhoneMock(donor.getPhone()))
                return false;
            donor.setId(mockIdCounter++);
            mockDonors.add(donor);
            return true;
        }

        if (existsByPhoneDB(donor.getPhone()))
            return false;

        String sql = "INSERT INTO donors(name, age, gender, blood_group, phone, city, last_donation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    donor.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Searches donors by blood group and city.
     *
     * @param bloodGroup blood group filter
     * @param city       city filter
     * @return list of donors matching both filters
     */
    public List<Donor> searchDonors(String bloodGroup, String city) {
        if (mockMode) {
            return mockDonors.stream()
                    .filter(d -> d.getBloodGroup().equalsIgnoreCase(bloodGroup)
                            && d.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }

        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group = ? AND city = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            ps.setString(2, city);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    donors.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    /**
     * Fetches all donors from mock list or DB.
     *
     * @return list of all donors
     */
    public List<Donor> getAllDonors() {
        if (mockMode)
            return new ArrayList<>(mockDonors);

        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donors";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                donors.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    /**
     * Updates donor's phone and city by ID.
     *
     * @param id    donor ID
     * @param phone new phone number
     * @param city  new city
     * @return true if update successful, false otherwise
     */
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

    /**
     * Deletes donor by ID.
     *
     * @param id donor ID
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteDonor(int id) {
        if (mockMode)
            return mockDonors.removeIf(d -> d.getId() == id);

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

    /**
     * Retrieves a donor by phone number.
     *
     * @param phone donor phone number
     * @return donor object or null if not found
     */
    public Donor getDonorByPhone(String phone) {
        if (mockMode) {
            return mockDonors.stream()
                    .filter(d -> phone.equals(d.getPhone()))
                    .findFirst()
                    .orElse(null);
        }

        String sql = "SELECT * FROM donors WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all donors by city.
     *
     * @param city city filter
     * @return list of donors from that city
     */

    public List<Donor> getDonorsByCity(String city) {
        if (mockMode) {
            return mockDonors.stream()
                    .filter(d -> d.getCity() != null && d.getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE city = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves all donors by city.
     *
     * @param city city filter
     * @return list of donors from that city
     */

    public List<Donor> getDonorsByBloodGroup(String bloodGroup) {
        if (mockMode) {
            return mockDonors.stream()
                    .filter(d -> d.getBloodGroup() != null && d.getBloodGroup().equalsIgnoreCase(bloodGroup))
                    .collect(Collectors.toList());
        }
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE blood_group = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bloodGroup);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves donors eligible for donation.
     * A donor is eligible if last donation is null or before cutoff (months).
     *
     * @param months minimum months since last donation
     * @return list of eligible donors
     */
    public List<Donor> getEligibleDonors(int months) {
        if (mockMode) {
            final LocalDate cutoff = LocalDate.now().minusMonths(months);
            return mockDonors.stream()
                    .filter(d -> d.getLastDonationDate() == null
                            || d.getLastDonationDate().isBefore(cutoff)
                            || d.getLastDonationDate().isEqual(cutoff))
                    .collect(Collectors.toList());
        }
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors WHERE last_donation_date IS NULL OR last_donation_date <= DATE_SUB(CURDATE(), INTERVAL ? MONTH)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, months);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Counts donors grouped by blood group.
     *
     * @return map of blood group → count
     */
    public Map<String, Integer> countDonorsByBloodGroup() {
        if (mockMode) {
            Map<String, Integer> map = new LinkedHashMap<>();
            for (Donor d : mockDonors) {
                String bg = d.getBloodGroup() == null ? "Not Specified" : d.getBloodGroup();
                map.put(bg, map.getOrDefault(bg, 0) + 1);
            }
            return map;
        }

        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT blood_group, COUNT(*) AS cnt FROM donors GROUP BY blood_group";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String bg = rs.getString("blood_group") == null ? "Not Specified" : rs.getString("blood_group");
                map.put(bg, rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Counts donors grouped by city and blood group.
     *
     * @return map of "city - bloodGroup" → count
     */
    public Map<String, Integer> countDonorsByCityAndBloodGroup() {
        if (mockMode) {
            Map<String, Integer> map = new LinkedHashMap<>();
            for (Donor d : mockDonors) {
                String city = d.getCity() == null ? "Not Specified" : d.getCity();
                String bg = d.getBloodGroup() == null ? "Not Specified" : d.getBloodGroup();
                String key = city + " - " + bg;
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            return map;
        }

        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT city, blood_group, COUNT(*) AS cnt FROM donors GROUP BY city, blood_group ORDER BY city, blood_group";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String city = rs.getString("city") == null ? "Not Specified" : rs.getString("city");
                String bg = rs.getString("blood_group") == null ? "Not Specified" : rs.getString("blood_group");
                map.put(city + " - " + bg, rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Counts total eligible donors.
     *
     * @param months months since last donation
     * @return count of eligible donors
     */
    public int countEligibleDonors(int months) {
        if (mockMode) {
            return (int) mockDonors.stream()
                    .filter(d -> d.getLastDonationDate() == null
                            || d.getLastDonationDate().isBefore(LocalDate.now().minusMonths(months))
                            || d.getLastDonationDate().isEqual(LocalDate.now().minusMonths(months)))
                    .count();
        }

        String sql = "SELECT COUNT(*) FROM donors WHERE last_donation_date IS NULL OR last_donation_date <= DATE_SUB(CURDATE(), INTERVAL ? MONTH)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, months);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Imports donors from a CSV file.
     * <p>
     * CSV format:
     * 
     * <pre>
     * name,age,gender,blood_group,phone,city,last_donation_date
     * </pre>
     * </p>
     *
     * @param filename CSV file path
     * @return ImportResult containing stats and errors
     */

    public ImportResult importDonorsFromCsv(String filename) {
        ImportResult result = new ImportResult();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine && line.toLowerCase().contains("name,age,gender")) {
                    firstLine = false;
                    continue;
                }
                result.incTotal();
                String[] parts = line.split(",", -1);
                if (parts.length < 6) {
                    result.incSkipped();
                    result.addError("Row " + result.getTotal() + ": Not enough columns");
                    continue;
                }

                try {
                    String name = parts[0].trim();
                    int age = Integer.parseInt(parts[1].trim());
                    String gender = parts[2].trim();
                    String bg = parts[3].trim().toUpperCase();
                    String phone = parts[4].trim();
                    String city = parts[5].trim();
                    String dateStr = parts.length > 6 ? parts[6].trim() : "";

                    if (name.isEmpty() || age < 18 ||
                            !phone.matches("\\d{10}") ||
                            !isValidBloodGroup(bg)) {
                        result.incSkipped();
                        result.addError("Row " + result.getTotal() + ": Invalid data");
                        continue;
                    }

                    LocalDate lastDate = null;
                    if (!dateStr.isEmpty()) {
                        try {
                            lastDate = LocalDate.parse(dateStr);
                        } catch (Exception e) {
                            result.incSkipped();
                            result.addError("Row " + result.getTotal() + ": Invalid date");
                            continue;
                        }
                    }

                    if (getDonorByPhone(phone) != null) {
                        result.incSkipped();
                        result.addError("Row " + result.getTotal() + ": Duplicate phone " + phone);
                        continue;
                    }

                    Donor donor = new Donor(0, name, age, gender, bg, phone, city, lastDate);
                    boolean ok = addDonor(donor);
                    if (ok)
                        result.incSuccess();
                    else {
                        result.incSkipped();
                        result.addError("Row " + result.getTotal() + ": Insert failed");
                    }

                } catch (Exception e) {
                    result.incSkipped();
                    result.addError("Row " + result.getTotal() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            result.addError("File error: " + e.getMessage());
        }
        return result;
    }

    private boolean isValidBloodGroup(String bg) {
        String[] groups = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
        for (String g : groups)
            if (g.equalsIgnoreCase(bg))
                return true;
        return false;
    }

    // --- helper methods ---

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

    private boolean existsByPhoneMock(String phone) {
        return mockDonors.stream()
                .anyMatch(d -> d.getPhone() != null && d.getPhone().equals(phone));
    }

    private boolean existsByPhoneDB(String phone) {
        String sql = "SELECT COUNT(*) FROM donors WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
