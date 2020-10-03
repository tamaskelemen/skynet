package com.skynet.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.skynet.dto.ScreenGpsCoordinates;
import com.skynet.jpa.CompanyJPA;
import com.skynet.repositories.CompanyRepository;
import com.skynet.repositories.CompanyRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

	@Autowired
	CompanyRepositoryJPA companyRepositoryJPA;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	MongoClient mongoClient;

	public void insertTestData() {
		CompanyJPA companyJPA = new CompanyJPA();
		companyJPA.setName("test company");
		companyJPA.setLocation(new Point(new Position(10.0, 15.0)));
		companyJPA.setProject("test project");
		companyRepositoryJPA.save(companyJPA);
	}

	public String getTestData() {
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "mongodb_data");
		BasicQuery basicQuery = new BasicQuery("{project: \"test project\"}");
		String doc = mongoOps.findOne(basicQuery, String.class, "company");
		return doc;
	}

	public List<String> getCompaniesInArea(ScreenGpsCoordinates screenGpsCoordinates) {
		Double startingLatitude = Double.parseDouble(screenGpsCoordinates.getStartPointPosition().getLatitude());
		Double startingLongitude = Double.parseDouble(screenGpsCoordinates.getStartPointPosition().getLongitude());
		Double endingLatitude = Double.parseDouble(screenGpsCoordinates.getEndPointPosition().getLatitude());
		Double endingLongitude = Double.parseDouble(screenGpsCoordinates.getEndPointPosition().getLongitude());

		return companyRepository.getCompaniesInArea(startingLatitude, startingLongitude, endingLatitude, endingLongitude);
	}
}
