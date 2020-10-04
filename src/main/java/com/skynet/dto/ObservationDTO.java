package com.skynet.dto;

import org.bson.types.ObjectId;

import java.util.Date;

public class ObservationDTO {

    private ObjectId id;
    private String observationNumber;
    private String observationElevation;
    private String fog;
    private String smoke;
    private String haze;
    private String dust;
    private String sand;
    private GpsCoordinate location;
    private Date observationDate;

    public Date getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(Date observationDate) {
        this.observationDate = observationDate;
    }

    public GpsCoordinate getLocation() {
        return location;
    }

    public void setLocation(GpsCoordinate location) {
        this.location = location;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getObservationNumber() {
        return observationNumber;
    }

    public void setObservationNumber(String observationNumber) {
        this.observationNumber = observationNumber;
    }

    public String getObservationElevation() {
        return observationElevation;
    }

    public void setObservationElevation(String observationEvaluation) {
        this.observationElevation = observationEvaluation;
    }

    public String getFog() {
        return fog;
    }

    public void setFog(String fog) {
        this.fog = fog;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getHaze() {
        return haze;
    }

    public void setHaze(String haze) {
        this.haze = haze;
    }

    public String getDust() {
        return dust;
    }

    public void setDust(String dust) {
        this.dust = dust;
    }

    public String getSand() {
        return sand;
    }

    public void setSand(String sand) {
        this.sand = sand;
    }
}
