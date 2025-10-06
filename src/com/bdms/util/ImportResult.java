package com.bdms.util;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {
    private int total;
    private int success;
    private int skipped;
    private final List<String> errors = new ArrayList<>();

    // --- Increment methods (needed by DonorDAO) ---
    public void incTotal() {
        total++;
    }

    public void incSuccess() {
        success++;
    }

    public void incSkipped() {
        skipped++;
    }

    // --- Error collector ---
    public void addError(String error) {
        errors.add(error);
    }

    // --- Getters ---
    public int getTotal() {
        return total;
    }

    public int getSuccess() {
        return success;
    }

    public int getSkipped() {
        return skipped;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "ImportResult{" +
                "total=" + total +
                ", success=" + success +
                ", skipped=" + skipped +
                ", errors=" + errors +
                '}';
    }
}
