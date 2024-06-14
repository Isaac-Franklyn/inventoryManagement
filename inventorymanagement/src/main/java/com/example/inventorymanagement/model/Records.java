package com.example.inventorymanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "records")
public class Records {
	
	@Id private String id;
	private String part_number;
	private String classification;
	private long total_price;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPart_number() {
		return part_number;
	}
	public void setPart_number(String part_number) {
		this.part_number = part_number;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public long getTotal_price() {
		return total_price;
	}
	public void setTotal_price(long total_price) {
		this.total_price = total_price;
	}
	
}
