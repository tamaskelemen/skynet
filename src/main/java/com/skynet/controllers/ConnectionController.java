package com.skynet.controllers;

import com.skynet.dto.CompanyConnectionDto;
import com.skynet.dto.ContractDTO;
import com.skynet.dto.GpsCoordinate;
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
    public List<CompanyConnectionDto> getConnections() {
        var connections = new ArrayList<CompanyConnectionDto>();
        CompanyConnectionDto connection = new CompanyConnectionDto();
        connection.setName("test");
        connection.setProject("test-project");
        connection.setLocation(new GpsCoordinate("48.04", "17.00"));

        var sub = new CompanyConnectionDto();
        var contract = new ContractDTO();
        contract.setDescription("test description");
        contract.setPrice("10000");
        contract.setStartDate(new Date(2010, 03, 22));
        contract.setEndDate(new Date(2015, 03, 22));
        sub.setContract(contract);
        sub.setLocation(new GpsCoordinate("48.04", "20.00"));
        sub.setName("test-sub-name");

        connection.getSub().add(sub);
        connections.add(connection);

        return connections;
    }

}
