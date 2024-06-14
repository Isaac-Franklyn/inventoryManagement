package com.example.inventorymanagement.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.inventorymanagement.model.Inventory;
import com.example.inventorymanagement.model.Records;

import com.example.inventorymanagement.repository.InventoryRepository;
import com.example.inventorymanagement.repository.RecordsRepository;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	RecordsRepository recordsRepository;
	
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	//creating part in inventory
	public List<Inventory> createPart(List<Inventory> inventoryParts) {
		return inventoryRepository.saveAll(inventoryParts);
	}
	
	//getting part in inventory by it's id
	public ResponseEntity<Optional<Inventory>> getPartById(String id){
		Optional<Inventory> inventory = inventoryRepository.findById(id);
		if(inventory.isPresent()) {
			return ResponseEntity.ok(inventory);
		}
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(inventory);
	}
	
	//getting all parts in inventory
	public List<Inventory> getInventoryParts(){
		return inventoryRepository.findAll();
	}
	
	//deleting part by it's id
	public ResponseEntity<String> deletePartByID(String id){
		Optional<Inventory> inventory = inventoryRepository.findById(id);
		if(inventory.isPresent()) {
			inventoryRepository.deleteById(id);
			return ResponseEntity.ok(id.concat(" has been deleted"));
		}
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id not found");
	}
	
	//deleting all parts in inventory
	public String deleteAllParts() {
		inventoryRepository.deleteAll();
		
		return "all parrts have been deleted";
	}
	
	//updating part in inventory
	public ResponseEntity<Inventory> updatePart(String id, Inventory inventoryPart){
		Optional<Inventory> inventoryNew = inventoryRepository.findById(id);
		if(inventoryNew.isPresent()) {
			Inventory inventory = inventoryNew.get();
			inventory.setClassification(inventoryPart.getClassification());
			inventory.setPart_number(inventoryPart.getPart_number());
			inventory.setPrice(inventoryPart.getPrice());
			inventory.setTotal_price(inventoryPart.getTotal_price());
			
			return ResponseEntity.ok(inventoryRepository.save(inventory));
			
		}else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	// Method to find similar part numbers using MongoDB aggregation
	 @Async("taskExecutor")
	    @Scheduled(cron = "0 0 * * * *")
    public CompletableFuture<List<Records>> calculatePrice() {
    	GroupOperation groupByPartClass = Aggregation.group("part_number", "classification").sum("price").as("total_price");
    	
    	// Project the fields you want in the output
        ProjectionOperation project = Aggregation.project()
                                                .and("_id.part_number").as("part_number")
                                                .and("_id.classification").as("classification")
                                                .andExclude("_id")
                                                .and("total_price").as("total_price");
        
    	
    	
    	Aggregation aggregate = Aggregation.newAggregation(groupByPartClass, project);
    	
    	AggregationResults<Records> results = mongoTemplate.aggregate(aggregate, "inventory", Records.class);

    	List<Records> resultList = results.getMappedResults();
    	
    	// Generate random id for each Records object
        for (Records record : resultList) {
            record.setId(UUID.randomUUID().toString()); // Generate random UUID as id
        }
    	
    	
    	return CompletableFuture.completedFuture(recordsRepository.saveAll(resultList));
    }
}
