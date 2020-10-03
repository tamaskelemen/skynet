package com.skynet.controllers;

import com.skynet.dto.ConnectionDTO;
import com.skynet.dto.GpsCoordinate;
import com.skynet.dto.Sub;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ConnectionDTO> getConnections() {
        var connections = new ArrayList<ConnectionDTO>();
        var connection = new ConnectionDTO();
        connection.setName("test");
        connection.setProject("test-project");
        connection.setLocation(new GpsCoordinate("48.04", "17.00"));

        var sub = new Sub();
        sub.setDescription("test description");
        sub.setLocation(new GpsCoordinate("48.04", "20.00"));
        sub.setName("test-sub-name");
        sub.setPrice("10000");
        sub.setStartDate(new Date(2010, 03, 22));
        sub.setEndDate(new Date(2015, 03, 22));

        connection.getSub().add(sub);
        connections.add(connection);

        return connections;
    }
}
