package com.skynet.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sub {
    private String description;
    private String price;
    private Date startDate;
    private Date endDate;
    private String name;
    private GpsCoordinate location;
    private List<Sub> sub = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GpsCoordinate getLocation() {
        return location;
    }

    public void setLocation(GpsCoordinate location) {
        this.location = location;
    }

    public List<Sub> getSub() {
        return sub;
    }

    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }
}
