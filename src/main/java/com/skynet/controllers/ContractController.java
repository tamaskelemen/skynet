package com.skynet.controllers;

import com.skynet.dto.CompanyConnectionDto;
import com.skynet.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

	@Autowired
	ContractService contractService;

	@GetMapping(value = "/get-simple-contracts",
			produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public List<CompanyConnectionDto> getSimpleContracts() {
		return contractService.getSimpleContracts();
	}
}
