package com.skynet.repositories;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClubsRepository {

    @Autowired
    private MongoClient client;

    public List<String> getAll() {
        MongoOperations mongoOps = new MongoTemplate(client, "mongodb_data");
        return mongoOps.findAll(String.class, "usa_astroclubs");
    }
}
