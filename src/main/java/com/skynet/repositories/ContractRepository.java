package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContractRepository {

	@Autowired
	MongoClient mongoClient;

	public List<String> getSimpleContracts() {
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "mongodb_data");
		List<String> doc = mongoOps.find(new Query(), String.class, "simple_contracts");
		return doc;
	}

	public List<String> getContractsWithoutProjects() {
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "mongodb_data");
		List<String> doc = mongoOps.find(new BasicQuery("{}"), String.class, "contract");
		return doc;
	}
}
