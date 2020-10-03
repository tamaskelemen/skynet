package com.skynet.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.skynet.jpa.CompanyJPA;
import com.skynet.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	MongoClient mongoClient;

	public void insertTestData() {
		CompanyJPA companyJPA = new CompanyJPA();
		companyJPA.setName("test company");
		companyJPA.setLocation(new Point(new Position(10.0, 15.0)));
		companyJPA.setProject("test project");
		companyRepository.save(companyJPA);
	}

	public String getTestData() {
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "mongodb_data");
		String doc = mongoOps.findOne(new Query(), String.class, "company");
		return doc;
	}
}
