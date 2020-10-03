package com.skynet.jpa;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "company")
public class CompanyJPA implements Serializable {
    private List<String> project = new ArrayList<>();
    private String name;
    private Point location;

    public CompanyJPA(){

    }

    public List<String> getProject() {
        return project;
    }

    public void setProject(List<String> project) {
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
}
