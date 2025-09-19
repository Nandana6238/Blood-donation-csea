package com.bdms.test;

import com.bdms.service.DonorService;
import com.bdms.util.ImportResult;

public class TestCsvImport {
    public static void main(String[] args) {
        System.out.println("=== CSV Import Test ===");

        // Use mock mode first (true = mock, false = DB)
        DonorService service = new DonorService(true);

        // File path relative to project root
        String file = "sample_data/new_donors.csv";

        ImportResult result = service.importDonorsFromCsv(file);

        // Print summary
        System.out.println("Total rows: " + result.getTotal());
        System.out.println("Imported successfully: " + result.getSuccess());
        System.out.println("Skipped (duplicates/invalid): " + result.getSkipped());

        if (!result.getErrors().isEmpty()) {
            System.out.println("\nErrors:");
            for (String err : result.getErrors()) {
                System.out.println("  - " + err);
            }
        }
    }
}
