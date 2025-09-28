package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {

    private final DonorService donorService;
    private JTable donorTable;
    private DefaultTableModel tableModel;

    // Input fields
    private JTextField idField, nameField, ageField, phoneField, cityField, lastDonationField;
    private JComboBox<String> genderBox, bloodGroupBox;

    public MainFrame() {
        donorService = new DonorService(false); // false = DB mode, true = mock mode
        initUI();
    }

    private void initUI() {
        setTitle("Blood Donor Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table setup
        String[] columns = { "ID", "Name", "Age", "Gender", "Blood Group", "Phone", "City", "Last Donation" };
        tableModel = new DefaultTableModel(columns, 0);
        donorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donorTable);

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

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Donor");
        JButton viewBtn = new JButton("View All");
        JButton searchBtn = new JButton("Search");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // Action Listeners
        addBtn.addActionListener(e -> addDonor());
        viewBtn.addActionListener(e -> loadAllDonors());
        searchBtn.addActionListener(e -> searchDonors());
        updateBtn.addActionListener(e -> updateDonor());
        deleteBtn.addActionListener(e -> deleteDonor());

        // Layout
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addDonor() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = (String) genderBox.getSelectedItem();
            String phone = phoneField.getText();
            String city = cityField.getText();
            String bloodGroup = (String) bloodGroupBox.getSelectedItem();
            String lastDonationStr = lastDonationField.getText();

            Donor donor = new Donor(
                    name,
                    age,
                    gender,
                    bloodGroup,
                    phone,
                    city,
                    lastDonationStr.isEmpty() ? null : LocalDate.parse(lastDonationStr));

            if (donorService.addDonor(donor)) {
                JOptionPane.showMessageDialog(this, "Donor added successfully!");
                loadAllDonors();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add donor (maybe duplicate phone).");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadAllDonors() {
        List<Donor> donors = donorService.getAllDonors();
        refreshTable(donors);
    }

    private void searchDonors() {
        String bg = (String) bloodGroupBox.getSelectedItem();
        String city = cityField.getText().trim().isEmpty() ? null : cityField.getText().trim();
        List<Donor> donors = donorService.searchDonors(bg, city);
        refreshTable(donors);
    }

    private void updateDonor() {
        try {
            int id = Integer.parseInt(idField.getText());
            String phone = phoneField.getText();
            String city = cityField.getText();

            if (donorService.updateDonor(id, phone, city)) {
                JOptionPane.showMessageDialog(this, "Donor updated successfully!");
                loadAllDonors();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteDonor() {
        try {
            int id = Integer.parseInt(idField.getText());
            if (donorService.deleteDonor(id)) {
                JOptionPane.showMessageDialog(this, "Donor deleted successfully!");
                loadAllDonors();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refreshTable(List<Donor> donors) {
        tableModel.setRowCount(0);
        for (Donor d : donors) {
            tableModel.addRow(new Object[] {
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
