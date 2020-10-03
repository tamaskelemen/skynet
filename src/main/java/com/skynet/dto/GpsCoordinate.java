package com.skynet.dto;

public class GpsCoordinate {

    private String latitude;
    private String longitude;

    public GpsCoordinate() {

    }

    public GpsCoordinate(String lat, String lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String lat) {
        this.latitude = lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String lon) {
        this.longitude = lon;
    }
}
