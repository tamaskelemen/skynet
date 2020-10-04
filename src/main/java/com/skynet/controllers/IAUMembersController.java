package com.skynet.controllers;

import com.skynet.dto.IAUMembersDTO;
import com.skynet.service.IAUMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class IAUMembersController {

    @Autowired
    private IAUMembersService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public List<IAUMembersDTO> getAll() {
        return service.findAll();
    }
}
