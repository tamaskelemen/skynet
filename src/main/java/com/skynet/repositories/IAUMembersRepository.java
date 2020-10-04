package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IAUMembersRepository {

    @Autowired
    MongoClient mongoClient;

    public List<String> findAll() {
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongodb_data");
        return mongoOps.find(new BasicQuery("{}"), String.class, "iau_members");
    }
}
