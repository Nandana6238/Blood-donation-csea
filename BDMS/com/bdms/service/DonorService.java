package BDMS.com.bdms.service;

import BDMS.com.bdms.dao.DonorDAO;
import BDMS.com.bdms.model.Donor;
import java.util.List;

public class DonorService {
    private final DonorDAO dao = new DonorDAO();

    public List<Donor> search(String bloodGroup, String city) {
        return dao.searchDonors(bloodGroup, city);
    }
}
