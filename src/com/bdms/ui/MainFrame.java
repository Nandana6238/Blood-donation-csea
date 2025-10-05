package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;
import com.bdms.service.DonationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {

    private final DonorService donorService;
    private final DonationService donationService;

    private JTabbedPane tabbedPane;

    // Donor panel components
    private JTable donorTable;
    private DefaultTableModel donorTableModel;
    private JTextField idField, nameField, ageField, phoneField, cityField, lastDonationField;
    private JComboBox<String> genderBox, bloodGroupBox;

    public MainFrame() {
        donorService = new DonorService(false); // DB mode
        donationService = new DonationService();
        initUI();
    }

    private void initUI() {
        setTitle("Blood Donor Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Dashboard
        DashboardPanel dashboardPanel = new DashboardPanel(donationService, this);

        tabbedPane.addTab("Dashboard", dashboardPanel);

        // Donor Management
        JPanel donorPanel = createDonorPanel();
        tabbedPane.addTab("Donors", donorPanel);

        // Donation Management
        DonationPanel donationPanel = new DonationPanel(donationService);
        tabbedPane.addTab("Donations", donationPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Method to switch tab by title
    public void switchToTab(String title) {
        int index = tabbedPane.indexOfTab(title);
        if (index != -1)
            tabbedPane.setSelectedIndex(index);
    }

    // ---------------- Donor Panel ----------------
    private JPanel createDonorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 6, 10, 10));
        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        phoneField = new JTextField();
        cityField = new JTextField();
        lastDonationField = new JTextField();
        genderBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        bloodGroupBox = new JComboBox<>(new String[] { "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-" });

        inputPanel.add(new JLabel("ID (for update/delete):"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderBox);
        inputPanel.add(new JLabel("Blood Group:"));
        inputPanel.add(bloodGroupBox);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(cityField);
        inputPanel.add(new JLabel("Last Donation (YYYY-MM-DD):"));
        inputPanel.add(lastDonationField);

        // Donor table
        donorTableModel = new DefaultTableModel(
                new String[] { "ID", "Name", "Age", "Gender", "Blood Group", "Phone", "City", "Last Donation" }, 0);
        donorTable = new JTable(donorTableModel);
        JScrollPane tableScroll = new JScrollPane(donorTable);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Donor");
        JButton viewBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> addDonor());
        viewBtn.addActionListener(e -> loadAllDonors());
        searchBtn.addActionListener(e -> searchDonors());
        updateBtn.addActionListener(e -> updateDonor());
        deleteBtn.addActionListener(e -> deleteDonor());

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------- Donor CRUD ----------------
    private void addDonor() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = (String) genderBox.getSelectedItem();
            String bloodGroup = (String) bloodGroupBox.getSelectedItem();
            String phone = phoneField.getText();
            String city = cityField.getText();
            String lastDonationStr = lastDonationField.getText();
            LocalDate lastDonation = lastDonationStr.isEmpty() ? null : LocalDate.parse(lastDonationStr);

            Donor donor = new Donor(0, name, age, gender, bloodGroup, phone, city, lastDonation);

            if (donorService.addDonor(donor)) {
                JOptionPane.showMessageDialog(this, " Donor added successfully!");
                loadAllDonors();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add donor (duplicate phone?).");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadAllDonors() {
        List<Donor> donors = donorService.getAllDonors();
        refreshDonorTable(donors);
    }

    private void searchDonors() {
        String bg = (String) bloodGroupBox.getSelectedItem();
        String city = cityField.getText().trim().isEmpty() ? null : cityField.getText().trim();
        List<Donor> donors = donorService.searchDonors(bg, city);
        refreshDonorTable(donors);
    }

    private void updateDonor() {
        try {
            int id = Integer.parseInt(idField.getText());
            String phone = phoneField.getText();
            String city = cityField.getText();
            boolean updated = donorService.updateDonor(id, phone, city);
            JOptionPane.showMessageDialog(this, updated ? "Donor updated!" : "Update failed!");
            loadAllDonors();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteDonor() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean deleted = donorService.deleteDonor(id);
            JOptionPane.showMessageDialog(this, deleted ? "Donor deleted!" : "Delete failed!");
            loadAllDonors();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refreshDonorTable(List<Donor> donors) {
        donorTableModel.setRowCount(0);
        for (Donor d : donors) {
            donorTableModel.addRow(new Object[] {
                    d.getId(), d.getName(), d.getAge(), d.getGender(),
                    d.getBloodGroup(), d.getPhone(), d.getCity(),
                    d.getLastDonationDate()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
