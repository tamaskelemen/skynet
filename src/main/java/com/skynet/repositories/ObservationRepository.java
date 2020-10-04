package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ObservationRepository {

    @Autowired
    MongoClient mongoClient;

    public List<String> findAll(Date startDate, Date endDate) {
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");
        return mongoOps.find(Query.query(Criteria.where("observation_date").gte(startDate).lt(endDate)),
                String.class,
                "globe_dust");
    }
}
