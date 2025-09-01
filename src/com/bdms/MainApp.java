package com.bdms;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        DonorDAO dao = new DonorDAO();
        List<Donor> donors = dao.getAllDonors();
        
        for (Donor d : donors) {
            System.out.println(d);
        }
    }
}
