package com.skynet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skynet.dto.CompanyConnectionDto;
import com.skynet.jpa.CompanyJPA;
import com.skynet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@GetMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public String testCompanySave() {
		try {
			companyService.insertTestData();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "asd";
	}

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE,
	 consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public CompanyConnectionDto testCompanyGet(@RequestParam String projectName) {
		return companyService.getCompanyByProjectName(projectName);
	}

}
