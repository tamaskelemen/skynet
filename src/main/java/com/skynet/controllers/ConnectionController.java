package com.skynet.controllers;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.skynet.dto.CompanyConnectionDto;
import com.skynet.dto.ContractDTO;
import com.skynet.dto.GpsCoordinate;
import com.skynet.dto.ScreenGpsCoordinates;
import com.skynet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public List<CompanyConnectionDto> getConnections() {
        var connections = new ArrayList<CompanyConnectionDto>();
        CompanyConnectionDto connection = new CompanyConnectionDto();
        connection.setName("test");
        connection.setProject(Arrays.asList("test-project"));
        connection.setLocation(new GpsCoordinate("20.04", "10.00"));

        var sub = new CompanyConnectionDto();
        var contract = new ContractDTO();
        contract.setDescription("test description");
        contract.setPrice("10000");
        contract.setStartDate(new Date(2010, 03, 22));
        contract.setEndDate(new Date(2015, 03, 22));
        sub.setContract(contract);
        sub.setLocation(new GpsCoordinate("40.04", "20.00"));
        sub.setName("test-sub-name");

        connection.getSub().add(sub);
        connections.add(connection);

        return connections;
    }

    @PostMapping(value = "/get-companies",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public List<String> getCompanies(@RequestBody ScreenGpsCoordinates screenGpsCoordinates) {
        return companyService.getCompaniesInArea(screenGpsCoordinates);
    }
}
