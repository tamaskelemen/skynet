package com.skynet.repositories;

import com.skynet.jpa.CompanyJPA;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepositoryJPA extends MongoRepository<CompanyJPA, String> {

}
