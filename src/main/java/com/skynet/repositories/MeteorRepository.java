package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Aggregates.match;

@Repository
public class MeteorRepository {

    @Autowired
    MongoClient mongoClient;

    public List<String> findAllMagnitude(Optional<String> shower, Date startDate, Date endDate) {
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "admin");

        if (!shower.isEmpty() && startDate != null && endDate != null) {
            return mongoOps.find(Query.query(new Criteria().andOperator(Criteria.where("start_date").gte(startDate).lt(endDate)
                            ,(Criteria.where("shower")).is(shower.get()))),
                    String.class,
                    "meteor_magnitude").stream().limit(100).collect(Collectors.toList());
        }

        if (!shower.isEmpty()) {
            return mongoOps.find(Query.query(Criteria.where("shower").is(shower.get())), String.class, "meteor_magnitude")
                    .stream()
                    .limit(100)
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            return mongoOps.find(Query.query(Criteria.where("start_date").gte(startDate)
                    .lt(endDate)), String.class, "meteor_magnitude")
                    .stream()
                    .limit(100)
                    .collect(Collectors.toList());
        }

        return mongoOps.find(new BasicQuery("{}"), String.class, "meteor_magnitude")
                .stream().limit(100).collect(Collectors.toList());
    }

    public String getSession(String sessionId) {
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "admin");
        return mongoOps.findOne(Query.query(Criteria.where("session_id").is(sessionId)),
                String.class, "meteor_session");
    }
}
