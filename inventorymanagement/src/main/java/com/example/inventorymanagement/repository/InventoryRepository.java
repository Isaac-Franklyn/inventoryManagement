package com.example.inventorymanagement.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.inventorymanagement.model.Inventory;


public interface InventoryRepository extends MongoRepository<Inventory, String>{
}
