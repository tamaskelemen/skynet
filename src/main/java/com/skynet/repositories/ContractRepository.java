package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class ContractRepository {

	@Autowired
	MongoClient mongoClient;

	public List<String> getSimpleContracts() {
		MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");
		List<String> doc = mongoOps.find(new Query(), String.class, "simple_contracts");
		return doc;
	}

	public List<String> getContractsWithoutProjects(String startDate, String endDate) {
		MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");

		if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
			return mongoOps.find(new Query(), String.class, "contract");
		}

		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			List<String> doc = mongoOps.find(
					Query.query(Criteria.where("startDate").gte(simpleDateFormat.parse(startDate)).lt(simpleDateFormat.parse(endDate))),
					String.class, "contract");
			return doc;
		} catch(Exception e) {
			return mongoOps.find(new Query(), String.class, "contract");
		}
	}
}
