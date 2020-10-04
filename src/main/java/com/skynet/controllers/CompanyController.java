package com.skynet.controllers;

import com.skynet.dto.CompanyConnectionDto;
import com.skynet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE,
	 consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public CompanyConnectionDto testCompanyGet(@RequestParam String projectName) {
		return companyService.getCompanyByProjectName(projectName);
	}

}
