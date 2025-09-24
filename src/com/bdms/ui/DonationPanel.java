
package com.bdms.ui;

import com.bdms.model.Donation;
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

        // Form inputs
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField donorIdField = new JTextField();
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField volumeField = new JTextField();
        JButton recordBtn = new JButton("Record Donation");

        form.add(new JLabel("Donor ID")); form.add(donorIdField);
        form.add(new JLabel("Date")); form.add(dateField);
        form.add(new JLabel("Volume (ml)")); form.add(volumeField);
        form.add(recordBtn);

        // Table setup
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Donor ID", "Date", "Volume"}, 0);
        donationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donationTable);

        add(form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button action
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

    private void refreshTable() {
        tableModel.setRowCount(0);
        donationService.getAllDonations()
                .forEach(d -> tableModel.addRow(new Object[]{
                        d.getId(),
                        d.getDonorId(),
                        d.getDate(),
                        d.getVolumeMl()
                }));
    }
}