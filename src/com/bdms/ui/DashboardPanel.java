package com.bdms.ui;

import com.bdms.service.DonationService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private DonationService donationService;
    private MainFrame mainFrame;

    public DashboardPanel(DonationService donationService, MainFrame mainFrame) {
        this.donationService = donationService;
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(3, 2, 20, 20));

        JButton donorBtn = new JButton("Donor Management");
        JButton donationBtn = new JButton("Donation Management");
        JButton exportBtn = new JButton("Export Donations CSV");
        JButton summaryBtn = new JButton("View Summary");
        JButton exitBtn = new JButton("Exit");

        donorBtn.addActionListener(e -> mainFrame.switchToTab("Donors"));
        donationBtn.addActionListener(e -> mainFrame.switchToTab("Donations"));
        exportBtn.addActionListener(e -> {
            String filename = JOptionPane.showInputDialog(this, "Enter filename (example.csv):");
            if (filename != null && !filename.isEmpty()) {
                boolean success = donationService.exportDonationSummaryCsv(filename);
                JOptionPane.showMessageDialog(this, success ? "✅ Export successful!" : "⚠ Export failed!");
            }
        });
        summaryBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Summary feature here!"));
        exitBtn.addActionListener(e -> System.exit(0));

        add(donorBtn);
        add(donationBtn);
        add(exportBtn);
        add(summaryBtn);
        add(exitBtn);
    }
}
