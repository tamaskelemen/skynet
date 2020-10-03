package com.skynet.dto;

public class ScreenGpsCoordinates {

    private GpsCoordinate endPointPosition;
    private GpsCoordinate startPointPosition;

    public GpsCoordinate getEndPointPosition() {
        return endPointPosition;
    }

    public void setEndPointPosition(GpsCoordinate endPointPosition) {
        this.endPointPosition = endPointPosition;
    }

    public GpsCoordinate getStartPointPosition() {
        return startPointPosition;
    }

    public void setStartPointPosition(GpsCoordinate startPointPosition) {
        this.startPointPosition = startPointPosition;
    }
}
