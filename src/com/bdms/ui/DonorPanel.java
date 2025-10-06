
package com.bdms.ui; 
 
import com.bdms.model.Donor; 
import com.bdms.service.DonorService; 
 
import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*; 
 
public class DonorPanel extends JPanel { 
    private DonorService donorService; 
    private JTable donorTable; 
    private DefaultTableModel tableModel; 
 
    public DonorPanel(DonorService donorService) { 
        this.donorService = donorService; 
        setLayout(new BorderLayout()); 
 
        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5)); 
        JTextField nameField = new JTextField(); 
        JTextField ageField = new JTextField(); 
        JTextField genderField = new JTextField(); 
        JTextField bloodGroupField = new JTextField(); 
        JTextField phoneField = new JTextField(); 
        JTextField cityField = new JTextField(); 
        JButton addBtn = new JButton("Add Donor"); 
 
        form.add(new JLabel("Name")); form.add(nameField); 
        form.add(new JLabel("Age")); form.add(ageField); 
        form.add(new JLabel("Gender")); form.add(genderField); 
        form.add(new JLabel("Blood Group")); form.add(bloodGroupField); 
        form.add(new JLabel("Phone")); form.add(phoneField); 
        form.add(new JLabel("City")); form.add(cityField); 
        form.add(addBtn); 
 
        tableModel = new DefaultTableModel( 
            new String[]{"ID","Name","Age","Gender","Blood Group","Phone","City"}, 0); 
        donorTable = new JTable(tableModel); 
        JScrollPane scrollPane = new JScrollPane(donorTable); 
 
        add(form, BorderLayout.NORTH); 
        add(scrollPane, BorderLayout.CENTER); 
 
        addBtn.addActionListener(e -> { 
            try { 
                Donor d = new Donor( 
                        nameField.getText(), 
                        Integer.parseInt(ageField.getText()), 
                        genderField.getText(), 
                        bloodGroupField.getText(), 
                        phoneField.getText(), 
                        cityField.getText(), 
                        null 
                ); 
                donorService.addDonor(d); 
                refreshTable(); 
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); 
            } 
        }); 
 
        refreshTable(); 
    } 
 
    private void refreshTable() { 
        tableModel.setRowCount(0); 
        for (Donor d : donorService.getAllDonors()) { 
            tableModel.addRow(new Object[]{ 
                d.getId(), d.getName(), d.getAge(), 
                d.getGender(), d.getBloodGroup(), 
                d.getPhone(), d.getCity() 
            }); 
        } 
    } 
} 
