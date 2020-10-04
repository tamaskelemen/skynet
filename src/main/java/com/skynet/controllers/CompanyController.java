package com.skynet.controllers;

import com.skynet.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

}
