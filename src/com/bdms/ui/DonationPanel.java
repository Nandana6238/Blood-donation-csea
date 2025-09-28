package com.bdms.ui;

import com.bdms.service.DonationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class DonationPanel extends JPanel {
    private DonationService donationService;
    private JTable donationTable;
    private DefaultTableModel tableModel;

    public DonationPanel(DonationService donationService) {
        this.donationService = donationService;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Form inputs
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                "Record Donation",
                0, 0, new Font("Arial", Font.BOLD, 14),
                new Color(0, 123, 255)));

        JTextField donorIdField = new JTextField();
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField volumeField = new JTextField();
        JButton recordBtn = new JButton("Record Donation");
        styleButton(recordBtn);

        form.add(new JLabel("Donor ID"));
        form.add(donorIdField);
        form.add(new JLabel("Date"));
        form.add(dateField);
        form.add(new JLabel("Volume (ml)"));
        form.add(volumeField);
        form.add(recordBtn);

        // Table setup
        tableModel = new DefaultTableModel(new String[] { "ID", "Donor ID", "Date", "Volume" }, 0);
        donationTable = new JTable(tableModel);
        donationTable.setBackground(Color.WHITE);
        donationTable.setForeground(Color.DARK_GRAY);
        donationTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        donationTable.getTableHeader().setBackground(new Color(0, 123, 255));
        donationTable.getTableHeader().setForeground(Color.WHITE);
        donationTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        donationTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(donationTable);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        recordBtn.addActionListener(e -> {
            try {
                int donorId = Integer.parseInt(donorIdField.getText());
                LocalDate date = LocalDate.parse(dateField.getText());
                int volume = Integer.parseInt(volumeField.getText());

                donationService.recordDonation(donorId, date, volume);
                refreshTable();
                JOptionPane.showMessageDialog(this, "✅ Donation recorded!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        refreshTable();
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(0, 123, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        donationService.getAllDonations().forEach(d -> tableModel
                .addRow(new Object[] { d.getId(), d.getDonorId(), d.getDonationDate(), d.getVolumeMl() }));
    }
}
