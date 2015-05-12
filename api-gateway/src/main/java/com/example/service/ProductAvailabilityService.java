package com.example.service;


import com.example.domain.ProductAvailability;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-10
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductAvailabilityService {
	public boolean isProductAvailable(String productId) {
		Client client = ClientBuilder.newClient();
		Invocation invocation = client.target("http://localhost:3000/api/availability/" + productId)
				.request()
				.buildGet();
		Response response = invocation.invoke();
		ProductAvailability availability = response.readEntity(ProductAvailability.class);
		return availability.isInStock();
	}
}
