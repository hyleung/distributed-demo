package com.example.domain;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-10
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unused")
public class ProductInfo {

	public ProductInfo() {
	}

	public ProductInfo(String id) {
		this.id = id;
	}

	String id;
	boolean inStock;
	double price;
	String name;
	String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
