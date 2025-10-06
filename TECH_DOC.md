🩸 Blood Donor Management System – Technical Documentation
🔎 Search Donors

Implemented in: DonorDAO.searchDonors(String bloodGroup, String city)

Description: Returns all donors matching the provided blood group and city.

Mock Mode: Uses in-memory mockDonors list with Java Streams filtering.

DB Mode: Executes SQL query:

SELECT * FROM donors WHERE blood_group = ? AND city = ?


Used By: DonorService.searchDonors() and menu option Search Donors (BloodGroup + City).

✏️ Update Donor

Implemented in: DonorDAO.updateDonor(int id, String phone, String city)

Description: Updates donor’s phone number and city.

Mock Mode: Iterates mockDonors list and modifies matching donor.

DB Mode: Executes SQL update with transaction safety:

UPDATE donors SET phone = ?, city = ? WHERE id = ?


Return Value:

true → update successful

false → donor not found or DB error

🗑️ Delete Donor

Implemented in: DonorDAO.deleteDonor(int id)

Description: Deletes donor by unique ID.

Mock Mode: Removes donor from mockDonors list.

DB Mode: Executes SQL delete:

DELETE FROM donors WHERE id = ?


Return Value:

true → deletion successful

false → donor not found or DB error

📥 Import Donors from CSV

Implemented in: DonorDAO.importDonorsFromCsv(String filename)

Wrapper: DonorService.importDonorsFromCsv(String filename)

Result Object: ImportResult (tracks total, success, skipped, error messages).

📄 CSV Format
name,age,gender,blood_group,phone,city,last_donation_date
Test User,25,F,AB+,9999900000,Delhi,2025-05-01
Another,30,M,O+,9999900001,Kochi,


Columns: name, age, gender, blood_group, phone, city, last_donation_date

last_donation_date optional; format = YYYY-MM-DD

✅ Validation Rules

name not empty

age ≥ 18

phone = exactly 10 digits

blood_group ∈ {A+, A-, B+, B-, AB+, AB-, O+, O-}

last_donation_date valid date or blank

Skip duplicates (existing phone in DB or earlier row in CSV)

🖥 Behavior

Each row processed:

If valid → donor inserted

If invalid/duplicate → skipped, error logged

Errors are written to:

imports/errors.txt

🧪 Testing

Test Class: TestCsvImport.java

Purpose: Runs CSV import with sample file sample_data/new_donors.csv.

Expected Output: Summary of total rows, success count, skipped count, and error details.