package com.bdms.service;

import com.bdms.dao.DonorDAO;
import com.bdms.model.Donor;
import com.bdms.util.ImportResult;

import java.util.List;
import java.util.Map;

/**
 * Service layer for donor operations.
 * Handles validation, business rules, and delegates to DonorDAO.
 */
public class DonorService {
    private final DonorDAO dao;

    /**
     * Constructs a new DonorService with mode selection.
     * 
     * @param mockMode true for in-memory mock data, false for DB mode.
     */
    public DonorService(boolean mockMode) {
        this.dao = new DonorDAO(mockMode);
    }

    /**
     * Adds a donor after validation.
     * 
     * @param donor Donor object containing donor details
     * @return true if added successfully, false if duplicate or error
     */

    public boolean addDonor(Donor donor) {
        return dao.addDonor(donor);
    }

    /**
     * Fetch all donors.
     * 
     * @return List of donors
     */
    public List<Donor> getAllDonors() {
        return dao.getAllDonors();
    }

    /**
     * Search donors by blood group and/or city.
     * 
     * @param bloodGroup required blood group (nullable for all groups)
     * @param city       city name (nullable for all cities)
     * @return matching donors
     */
    public List<Donor> searchDonors(String bloodGroup, String city) {
        return dao.searchDonors(bloodGroup, city);
    }

    /**
     * Update donor details.
     * 
     * @param id    donor ID
     * @param phone new phone number
     * @param city  new city
     * @return true if updated successfully
     */
    public boolean updateDonor(int id, String phone, String city) {
        return dao.updateDonor(id, phone, city);
    }

    /**
     * Delete donor by ID.
     * 
     * @param id donor ID
     * @return true if deleted
     */

    public boolean deleteDonor(int id) {
        return dao.deleteDonor(id);
    }

    /**
     * Find donor by phone number.
     * 
     * @param phone phone number
     * @return Donor or null if not found
     */
    public Donor findByPhone(String phone) {
        return dao.getDonorByPhone(phone);
    }

    // --- Reports ---

    /**
     * Get donors grouped by city.
     * 
     * @param city city name
     * @return donors in that city
     */

    public List<Donor> getDonorsByCity(String city) {
        return dao.getDonorsByCity(city);
    }

    /**
     * Get donors grouped by blood group.
     * 
     * @param bloodGroup blood group
     * @return donors with that blood group
     */
    public List<Donor> getDonorsByBloodGroup(String bg) {
        return dao.getDonorsByBloodGroup(bg);
    }

    /**
     * Get donors eligible to donate after specified months.
     * 
     * @param months number of months since last donation
     * @return eligible donors
     */
    public List<Donor> getEligibleDonors(int months) {
        return dao.getEligibleDonors(months);
    }

    /**
     * Count donors by blood group.
     * 
     * @return map of blood group to count
     */
    public Map<String, Integer> countDonorsByBloodGroup() {
        return dao.countDonorsByBloodGroup();
    }

    /**
     * Count donors by city and blood group.
     * 
     * @return map of "City|BloodGroup" to count
     */
    public Map<String, Integer> countDonorsByCityAndBloodGroup() {
        return dao.countDonorsByCityAndBloodGroup();
    }

    /**
     * Count eligible donors.
     * 
     * @param months months since last donation
     * @return number of eligible donors
     */
    public int countEligibleDonors(int months) {
        return dao.countEligibleDonors(months);
    }

    /**
     * Import donors from a CSV file.
     * 
     * @param filename path to CSV file
     * @return ImportResult containing summary and errors
     */
    public ImportResult importDonorsFromCsv(String filename) {
        return dao.importDonorsFromCsv(filename);
    }
}
