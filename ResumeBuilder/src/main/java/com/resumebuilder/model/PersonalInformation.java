package com.resumebuilder.model;

import java.util.ArrayList;
import java.util.List;

public class PersonalInformation {
    private List<String> fields;

    public PersonalInformation() {
        fields = new ArrayList<>();
        // Initialize with 10 fields: Full Name, Age, Sex, Date of Birth, Place of Birth, Citizenship, Height, Weight, Religion, Languages
        for (int i = 0; i < 10; i++) {
            fields.add("");
        }
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}