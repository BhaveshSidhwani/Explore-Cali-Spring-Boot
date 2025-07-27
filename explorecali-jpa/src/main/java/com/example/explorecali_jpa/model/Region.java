package com.example.explorecali_jpa.model;

public enum Region {
    Central_Coast("Central Coast"),
    Southern_California("Southern California"),
    Northern_California("Northern California"),
    Varies("Varies");

    private String label;

    private Region(String label) {
        this.label = label;
    }

    public static Region findByLabel(String label) {
        for(Region r:Region.values()) {
            if (r.label.equalsIgnoreCase(label))
                return r;
        }
        return null;
    }

    public String getLabel() {
        return this.label;
    }
}
