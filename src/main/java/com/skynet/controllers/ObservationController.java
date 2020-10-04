package com.skynet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skynet.dto.ObservationDTO;
import com.skynet.service.ObservationService;
import org.apache.tomcat.jni.Local;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/observation")
public class ObservationController {

    private ObservationService service;

    public ObservationController(ObservationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ObservationDTO> getAll(@RequestParam String startDate,
                                       @RequestParam String endDate
    ) {
        try {
            return service.getAll(LocalDate.parse(startDate), LocalDate.parse(endDate));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
