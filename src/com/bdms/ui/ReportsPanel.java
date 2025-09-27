
package com.bdms.ui; 
 
import com.bdms.service.DonorService; 
 
import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*; 
import java.util.Map; 
 
public class ReportsPanel extends JPanel { 
    private DonorService donorService; 
    private JTable reportTable; 
    private DefaultTableModel tableModel; 
 
    public ReportsPanel(DonorService donorService) { 
        this.donorService = donorService; 
        setLayout(new BorderLayout()); 
 
        JButton byGroupBtn = new JButton("Count by Blood Group"); 
        JButton byCityGroupBtn = new JButton("Count by City & Blood Group"); 
 
        JPanel topPanel = new JPanel(); 
        topPanel.add(byGroupBtn); 
        topPanel.add(byCityGroupBtn); 
 
        tableModel = new DefaultTableModel(new String[]{"Key","Count"}, 0); 
        reportTable = new JTable(tableModel); 
 
        add(topPanel, BorderLayout.NORTH); 
        add(new JScrollPane(reportTable), BorderLayout.CENTER); 
 
        byGroupBtn.addActionListener(e -> 
showReport(donorService.countDonorsByBloodGroup())); 
        byCityGroupBtn.addActionListener(e -> 
showReport(donorService.countDonorsByCityAndBloodGroup())); 
    } 
 
    private void showReport(Map<String,Integer> map) { 
        tableModel.setRowCount(0); 
        for (Map.Entry<String,Integer> entry : map.entrySet()) { 
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }
}