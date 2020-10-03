package com.skynet.controllers;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.skynet.dto.CompanyConnectionDto;
import com.skynet.dto.ContractDTO;
import com.skynet.dto.ScreenGpsCoordinates;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
        connection.setProject(Arrays.asList("test-project"));
        connection.setLocation(new Point(new Position(43.371745, -79.783879)));

        var sub = new CompanyConnectionDto();
        var contract = new ContractDTO();
        contract.setDescription("test description");
        contract.setPrice("10000");
        contract.setStartDate(new Date(2010, 03, 22));
        contract.setEndDate(new Date(2015, 03, 22));
        sub.setContract(contract);
        sub.setLocation(new Point(new Position(40.04, 20.00)));
        sub.setName("test-sub-name");

        connection.getSub().add(sub);
        connections.add(connection);

        return connections;
    }

    @PostMapping(value = "/get-companies",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public List<CompanyConnectionDto> getCompanies(@RequestBody ScreenGpsCoordinates screenGpsCoordinates) {
        return null;
    }
}
