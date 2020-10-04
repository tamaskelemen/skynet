package com.skynet.dto;

public class ClubsDTO {

    private String name;
    private String note;
    private GpsCoordinate location;

    public GpsCoordinate getLocation() {
        return location;
    }

    public void setLocation(GpsCoordinate location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
