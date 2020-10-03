package com.skynet.jpa;

import com.mongodb.client.model.geojson.Position;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "company")
public class CompanyJPA implements Serializable {
    private String project;
    private String name;
    private Position location;

    public CompanyJPA(){

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

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }
}
