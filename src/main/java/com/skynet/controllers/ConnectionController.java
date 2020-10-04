package com.skynet.controllers;

import com.skynet.dto.ScreenGpsCoordinates;
import com.skynet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/connection")
public class ConnectionController {

    @Autowired
    CompanyService companyService;

    @PostMapping(value = "/get-companies",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public List<String> getCompanies(@RequestBody ScreenGpsCoordinates screenGpsCoordinates) {
        return companyService.getCompaniesInArea(screenGpsCoordinates);
    }
}
