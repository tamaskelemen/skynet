package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepository {

	@Autowired
	MongoClient mongoClient;

	public List<String> getCompaniesInArea(String startingLatitude, String startingLongitude, String endingLatitude, String endingLongitude) {
		MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");
		String query = String.format("{location:{$geoWithin :{$box: [[%s,%s], [%s,%s]]}}}",startingLatitude, startingLongitude, endingLatitude, endingLongitude );
		List<String> doc = mongoOps.find(new BasicQuery(query), String.class, "company");
		return doc;
	}

	public List<String> getCompanies() {
		MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");
		List<String> doc = mongoOps.find(new Query(), String.class, "company");
		return doc;
	}
}
