package com.skynet.repositories;

import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ObservationRepository {

    public List<String> findAll(Date startDate, Date endDate) {
        MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "admin");
        return mongoOps.find(Query.query(Criteria.where("observation_date").gte(startDate).lt(endDate)),
                String.class,
                "globe_dust");
    }
}
