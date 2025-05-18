package com.resumebuilder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InputValidator {

    // Validate all relevant fields and return a list of error messages
    public static List<String> validateResumeData(com.resumebuilder.model.ResumeData data) {
        List<String> errors = new ArrayList<>();

        // Validate Personal Information
        List<String> personalFields = data.getPersonalInformation().getFields();
        validateAge(personalFields.get(1), errors);
        validateDateOfBirth(personalFields.get(3), errors);
        validateHeight(personalFields.get(6), errors);
        validateWeight(personalFields.get(7), errors);

        // Validate Contact Information
        List<String> contactFields = data.getContactInformation().getFields();
        validateContactNumber(contactFields.get(0), errors);
        validateEmail(contactFields.get(1), errors);

        // Validate Education
        com.resumebuilder.model.Education education = data.getEducation();
        validateAcademicYear(education.getCollegeYear(), "College Academic Year", errors);
        validateAcademicYear(education.getShsYear(), "Senior High School Academic Year", errors);
        validateAcademicYear(education.getJhsYear(), "Junior High School Academic Year", errors);

        return errors;
    }

    private static void validateAge(String age, List<String> errors) {
        if (age == null || age.trim().isEmpty()) return; // Allow empty age
        try {
            int ageValue = Integer.parseInt(age.trim());
            if (ageValue <= 0 || ageValue > 120) {
                errors.add("Age must be a number between 1 and 120.");
            }
        } catch (NumberFormatException e) {
            errors.add("Age must be a numerical value (e.g., 25).");
        }
    }

    private static void validateDateOfBirth(String dob, List<String> errors) {
        if (dob == null || dob.trim().isEmpty()) return; // Allow empty DOB
        // Check for MM/DD/YYYY or YYYY-MM-DD format
        if (!Pattern.matches("^((\\d{2}/\\d{2}/\\d{4})|(\\d{4}-\\d{2}-\\d{2}))$", dob.trim())) {
            errors.add("Date of Birth must be in MM/DD/YYYY or YYYY-MM-DD format (e.g., 01/01/1990).");
        }
    }

    private static void validateHeight(String height, List<String> errors) {
        if (height == null || height.trim().isEmpty()) return; // Allow empty height
        try {
            double heightValue = Double.parseDouble(height.trim());
            if (heightValue <= 0 || heightValue > 300) { // Reasonable range for height in cm
                errors.add("Height must be a number between 1 and 300 (in cm).");
            }
        } catch (NumberFormatException e) {
            errors.add("Height must be a numerical value (e.g., 170).");
        }
    }

    private static void validateWeight(String weight, List<String> errors) {
        if (weight == null || weight.trim().isEmpty()) return; // Allow empty weight
        try {
            double weightValue = Double.parseDouble(weight.trim());
            if (weightValue <= 0 || weightValue > 500) { // Reasonable range for weight in kg
                errors.add("Weight must be a number between 1 and 500 (in kg).");
            }
        } catch (NumberFormatException e) {
            errors.add("Weight must be a numerical value (e.g., 70).");
        }
    }

    private static void validateContactNumber(String number, List<String> errors) {
        if (number == null || number.trim().isEmpty()) return; // Allow empty contact number
        // Allow digits, spaces, dashes, and parentheses
        if (!Pattern.matches("^[0-9\\-\\s()]+$", number.trim())) {
            errors.add("Contact Number must contain only digits, spaces, dashes, or parentheses (e.g., 123-456-7890).");
        }
    }

    private static void validateEmail(String email, List<String> errors) {
        if (email == null || email.trim().isEmpty()) return; // Allow empty email
        // Basic email validation: must contain @ and a domain
        if (!Pattern.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email.trim())) {
            errors.add("Email Address must be a valid email (e.g., example@domain.com).");
        }
    }

    private static void validateAcademicYear(String year, String fieldName, List<String> errors) {
        if (year == null || year.trim().isEmpty()) return; // Allow empty academic year
        // Allow single year (e.g., "2020") or range (e.g., "2018-2020")
        if (!Pattern.matches("^(\\d{4})|(\\d{4}-\\d{4})$", year.trim())) {
            errors.add(fieldName + " must be a valid year or range (e.g., 2020 or 2018-2020).");
        } else if (year.contains("-")) {
            String[] years = year.split("-");
            int startYear = Integer.parseInt(years[0]);
            int endYear = Integer.parseInt(years[1]);
            if (startYear >= endYear || startYear < 1900 || endYear > 2100) {
                errors.add(fieldName + " range must be valid (start year < end year, between 1900 and 2100).");
            }
        } else {
            int singleYear = Integer.parseInt(year.trim());
            if (singleYear < 1900 || singleYear > 2100) {
                errors.add(fieldName + " must be between 1900 and 2100.");
            }
        }
    }
}