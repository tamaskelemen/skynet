package com.skynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.skynet.dto.ScreenGpsCoordinates;
import com.skynet.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	MongoClient mongoClient;

	@Autowired
	ObjectMapper objectMapper;

	public List<String> getCompaniesInArea(ScreenGpsCoordinates screenGpsCoordinates) {
		String startingLatitude = screenGpsCoordinates.getStartPointPosition().getLatitude();
		String startingLongitude = screenGpsCoordinates.getStartPointPosition().getLongitude();
		String endingLatitude = screenGpsCoordinates.getEndPointPosition().getLatitude();
		String endingLongitude = screenGpsCoordinates.getEndPointPosition().getLongitude();

		return companyRepository.getCompaniesInArea(startingLatitude, startingLongitude, endingLatitude, endingLongitude);
	}

	public List<String> getCompanies(){
		return companyRepository.getCompanies();
	}
}
