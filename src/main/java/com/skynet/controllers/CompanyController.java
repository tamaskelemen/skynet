package com.skynet.controllers;

import com.skynet.jpa.CompanyJPA;
import com.skynet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@GetMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public String testCompanySave() {
		companyService.insertTestData();
		return "asd";
	}

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public String testCompanyGet() {
		return companyService.getTestData();
	}

}
