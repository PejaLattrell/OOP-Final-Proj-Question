package com.resumebuilder.model;

import java.util.ArrayList;
import java.util.List;

public class ContactInformation {
    private List<String> fields;

    public ContactInformation() {
        fields = new ArrayList<>();
        // Initialize with 3 fields: Contact Number, Email Address, Address
        for (int i = 0; i < 3; i++) {
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