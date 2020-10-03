package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepository {

	@Autowired
	MongoClient mongoClient;

	public List<String> getCompaniesInArea(Double startingLatitude, Double startingLongitude, Double endingLatitude, Double endingLongitude) {
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "mongodb_data");
		String query = String.format("{location:{$geoWithin :{$box: [[%d,%d], [%d0,%d]]}}}",startingLatitude, startingLongitude, endingLatitude, endingLongitude );
		List<String> doc = mongoOps.find(new BasicQuery(query), String.class, "company");
		return doc;
	}

}
