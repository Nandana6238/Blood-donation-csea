package com.bdms.ui; 
 
import com.bdms.model.Request; 
import com.bdms.service.RequestService; 
 
import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*; 
import java.time.LocalDate; 
 
public class RequestPanel extends JPanel { 
    private RequestService requestService; 
    private JTable requestTable; 
    private DefaultTableModel tableModel; 
 
    public RequestPanel(RequestService requestService) { 
        this.requestService = requestService; 
        setLayout(new BorderLayout()); 
 
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5)); 
        JTextField nameField = new JTextField(); 
        JTextField bloodGroupField = new JTextField(); 
        JTextField cityField = new JTextField(); 
        JButton addBtn = new JButton("Submit Request"); 
 
        form.add(new JLabel("Recipient Name")); form.add(nameField); 
        form.add(new JLabel("Blood Group")); form.add(bloodGroupField); 
        form.add(new JLabel("City")); form.add(cityField); 
        form.add(addBtn); 
 
        tableModel = new DefaultTableModel( 
            new String[]{"ID","Recipient","Blood Group","City","Date"}, 0); 
        requestTable = new JTable(tableModel); 
        JScrollPane scrollPane = new JScrollPane(requestTable); 
 
        add(form, BorderLayout.NORTH); 
        add(scrollPane, BorderLayout.CENTER); 
 
        addBtn.addActionListener(e -> { 
            try { 
                Request r = new Request( 
                        nameField.getText(), 
                        bloodGroupField.getText(), 
                        cityField.getText(), 
                        LocalDate.now() 
                ); 
                requestService.addRequest(r); 
                refreshTable(); 
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); 
            } 
        }); 
 
        refreshTable(); 
    } 
 
    private void refreshTable() { 
        tableModel.setRowCount(0); 
        for (Request r : requestService.getAllRequests()) { 
            tableModel.addRow(new Object[]{ 
                r.getId(), r.getRecipientName(), r.getBloodGroup(), 
                r.getCity(), r.getRequestDate() 
            }); 
        } 
    } 
} 

