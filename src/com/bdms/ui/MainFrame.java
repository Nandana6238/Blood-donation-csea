package com.bdms.ui;

import com.bdms.model.Donor;
import com.bdms.service.DonorService;
import com.bdms.service.DonationService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {

    private final DonorService donorService;
    private final DonationService donationService;

    // Donor tab fields
    private JTable donorTable;
    private DefaultTableModel donorTableModel;
    private JTextField idField, nameField, ageField, phoneField, cityField, lastDonationField;
    private JComboBox<String> genderBox, bloodGroupBox;

    public MainFrame() {
        donorService = new DonorService(false);
        donationService = new DonationService();
        initUI();
    }

    private void initUI() {
        setTitle("Blood Donor Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modern look & feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Donors", createDonorPanel());
        tabs.addTab("Donations", new DonationPanel(donationService));

        add(tabs);
    }

    private JPanel createDonorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // ===== Table =====
        String[] donorColumns = { "ID", "Name", "Age", "Gender", "Blood Group", "Phone", "City", "Last Donation" };
        donorTableModel = new DefaultTableModel(donorColumns, 0);
        donorTable = new JTable(donorTableModel);
        donorTable.setBackground(Color.WHITE);
        donorTable.setForeground(Color.DARK_GRAY);
        donorTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        donorTable.getTableHeader().setBackground(new Color(0, 123, 255));
        donorTable.getTableHeader().setForeground(Color.WHITE);
        donorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        donorTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(donorTable);

        // ===== Input Form =====
        JPanel inputPanel = new JPanel(new GridLayout(3, 6, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                "Add / Update Donor",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(0, 123, 255)));

        idField = new JTextField();
        idField.setBackground(Color.WHITE);
        nameField = new JTextField();
        nameField.setBackground(Color.WHITE);
        ageField = new JTextField();
        ageField.setBackground(Color.WHITE);
        phoneField = new JTextField();
        phoneField.setBackground(Color.WHITE);
        cityField = new JTextField();
        cityField.setBackground(Color.WHITE);
        lastDonationField = new JTextField();
        lastDonationField.setBackground(Color.WHITE);

        genderBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        bloodGroupBox = new JComboBox<>(new String[] { "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-" });

        inputPanel.add(new JLabel("ID (update/delete):"));
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

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton addBtn = createButton("Add Donor");
        JButton viewBtn = createButton("View All");
        JButton searchBtn = createButton("Search");
        JButton updateBtn = createButton("Update");
        JButton deleteBtn = createButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // ===== Button actions =====
        addBtn.addActionListener(e -> addDonor());
        viewBtn.addActionListener(e -> loadAllDonors());
        searchBtn.addActionListener(e -> searchDonors());
        updateBtn.addActionListener(e -> updateDonor());
        deleteBtn.addActionListener(e -> deleteDonor());

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadAllDonors();
        return panel;
    }

    // ===== Helper to create styled buttons =====
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }

    // ===== Donor methods =====
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
                clearDonorFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add donor (maybe duplicate phone).");
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

    private void clearDonorFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        cityField.setText("");
        lastDonationField.setText("");
        genderBox.setSelectedIndex(0);
        bloodGroupBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
