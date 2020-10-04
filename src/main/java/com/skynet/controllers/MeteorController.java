package com.skynet.controllers;

import com.skynet.dto.MeteorDTO;
import com.skynet.service.MeteorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meteors")
public class MeteorController {

    @Autowired
    private MeteorService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://www.space-net.biz")
    public List<MeteorDTO> getAll(
            @RequestParam Optional<String> shower,
            @RequestParam Optional<String> startDate,
            @RequestParam Optional<String> endDate
    ) {


        return service.findAll(shower, startDate, endDate);
    }
}
