package com.bdms.test;

import com.bdms.service.DonorService;
import com.bdms.model.Donor;

import java.util.List;

public class TestReports {
    public static void main(String[] args) {
        DonorService service = new DonorService(true); // mock
        System.out.println("By city -> Delhi");
        List<Donor> byCity = service.getDonorsByCity("Delhi");
        System.out.println(byCity);

        System.out.println("By blood group -> A+");
        List<Donor> byBg = service.getDonorsByBloodGroup("A+");
        System.out.println(byBg);

        System.out.println("Eligible donors (3 months):");
        List<Donor> elig = service.getEligibleDonors(3);
        System.out.println(elig);
    }
}
