package com.skynet.dto;

import com.mongodb.client.model.geojson.Point;

import java.util.ArrayList;
import java.util.List;

public class CompanyConnectionDto {
    private String project;
    private String name;
    private Point location;
    private List<CompanyConnectionDto> sub = new ArrayList<>();
    private ContractDTO contract;

    public CompanyConnectionDto(){

    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public List<CompanyConnectionDto> getSub() {
        return sub;
    }

    public void setSub(List<CompanyConnectionDto> sub) {
        this.sub = sub;
    }

    public ContractDTO getContract() {
        return contract;
    }

    public void setContract(ContractDTO contract) {
        this.contract = contract;
    }
}
