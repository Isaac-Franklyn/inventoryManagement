package com.example.inventorymanagement.controller;

import java.util.List;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventorymanagement.model.Inventory;
import com.example.inventorymanagement.model.Records;
import com.example.inventorymanagement.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	//request to input a new part
	@PostMapping
	public List<Inventory> createPart(@RequestBody List<Inventory> parts) {
		return inventoryService.createPart(parts);
	}
	
	//request to get a part by it's serial number(id)
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Inventory>> getPartById(@RequestParam String id){
		return inventoryService.getPartById(id);
	}
	
	//request to get all parts
	@GetMapping
	public List<Inventory> getParts(){
		return inventoryService.getInventoryParts();
	}
	
	//request to delete by id
	@DeleteMapping
	public ResponseEntity<String> deletePart(@RequestParam String id){
		return inventoryService.deletePartByID(id);
	}
	
	//request to delete all parts from inventory
	@DeleteMapping("/delete")
	public String deleteParts() {
		return inventoryService.deleteAllParts();
	}
	
	//request to update a part by it's serial number
	@PutMapping("/{id}")
	public ResponseEntity<Inventory> updatePart(@RequestParam String id, @RequestBody Inventory part){
		return inventoryService.updatePart(id, part);
	}
	
    @GetMapping("/calculate")
    public CompletableFuture<List<Records>> calculatePrice() {
    	return inventoryService.calculatePrice();
    	}
	
}
