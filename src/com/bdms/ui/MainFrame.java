
package com.bdms.ui; 
 
import com.bdms.service.DonorService; 
import com.bdms.service.RequestService;
import com.bdms.model.Donor;
import com.bdms.model.Request;



 
import javax.swing.*; 
import java.awt.*; 
 
public class MainFrame extends JFrame { 
    private DonorService donorService; 
    private RequestService requestService; 
 
    public MainFrame() { 
        donorService = new DonorService(false); // DB mode 
        requestService = new RequestService(); 
 
        setTitle("Blood Donor Management System"); 
        setSize(800, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
 
        JTabbedPane tabs = new JTabbedPane(); 
        tabs.add("Donors", new DonorPanel(donorService)); 
        tabs.add("Requests", new RequestPanel(requestService)); 
        tabs.add("Donations", new DonationPanel(donorService)); 
        tabs.add("Reports", new ReportsPanel(donorService)); 
 
        add(tabs, BorderLayout.CENTER); 
        setVisible(true); 
    } 
 
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(MainFrame::new);
    }
}