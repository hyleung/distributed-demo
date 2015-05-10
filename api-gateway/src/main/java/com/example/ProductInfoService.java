package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-09
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/product")
public class ProductInfoService {
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductInfo retrieveInfo(@PathParam("id") String id) {
		return new ProductInfo(id);
	}

	@SuppressWarnings("unused")
	public static class ProductInfo {

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
}
