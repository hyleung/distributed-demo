package com.example.domain;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-11
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductAvailability {
	private int count;
	private String id;
	private boolean inStock;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

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
}
