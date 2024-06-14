package com.example.inventorymanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.inventorymanagement.model.Records;

@Repository
public interface RecordsRepository extends MongoRepository<Records, String>{

}
