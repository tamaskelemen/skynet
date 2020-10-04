package com.skynet.dto;

import java.util.ArrayList;
import java.util.List;

public class MeteorDTO {
    private GpsCoordinate location;
    private MagnitudeDTO magnitude;
    private String shower;

    public String getShower() {
        return shower;
    }

    public void setShower(String shower) {
        this.shower = shower;
    }

    public GpsCoordinate getLocation() {
        return location;
    }

    public void setLocation(GpsCoordinate location) {
        this.location = location;
    }

    public MagnitudeDTO getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(MagnitudeDTO magnitude) {
        this.magnitude = magnitude;
    }
}
