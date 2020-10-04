package com.skynet.controllers;

import com.skynet.dto.ClubsDTO;
import com.skynet.service.ClubsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubsController {

    @Autowired
    private ClubsService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://www.space-net.biz")
    public List<ClubsDTO> getAll() {
        return service.getAll();
    }
}
