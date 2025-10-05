package com.bdms.ui;

import com.bdms.model.Donation;
import com.bdms.service.DonationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DonationPanel extends JPanel {

    private DonationService donationService;
    private JTable donationTable;
    private DefaultTableModel tableModel;
    private JTextField donorIdField, dateField, volumeField;

    public DonationPanel(DonationService donationService) {
        this.donationService = donationService;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        donorIdField = new JTextField();
        dateField = new JTextField();
        volumeField = new JTextField();

        inputPanel.add(new JLabel("Donor ID:"));
        inputPanel.add(donorIdField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Volume (ml):"));
        inputPanel.add(volumeField);

        tableModel = new DefaultTableModel(new String[] { "ID", "Donor ID", "Date", "Volume" }, 0);
        donationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donationTable);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Donation");
        JButton viewBtn = new JButton("View All");
        JButton exportBtn = new JButton("Export CSV");

        addBtn.addActionListener(e -> addDonation());
        viewBtn.addActionListener(e -> loadAllDonations());
        exportBtn.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog(this, "Enter filename (example.csv):");
            if (filename != null && !filename.isEmpty()) {
                boolean success = donationService.exportDonationSummaryCsv(filename);
                JOptionPane.showMessageDialog(this, success ? "✅ Export successful!" : "⚠ Export failed!");
            }
        });

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(exportBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addDonation() {
        try {
            int donorId = Integer.parseInt(donorIdField.getText());
            LocalDate date = LocalDate.parse(dateField.getText());
            int volume = Integer.parseInt(volumeField.getText());
            donationService.recordDonation(donorId, date, volume);
            loadAllDonations();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadAllDonations() {
        List<Donation> donations = donationService.getAllDonations();
        tableModel.setRowCount(0);
        for (Donation d : donations) {
            tableModel.addRow(new Object[] { d.getId(), d.getDonorId(), d.getDate(), d.getVolumeMl() });
        }
    }
}
