package com.example.service;


import com.example.command.inventory.RetrieveProductAvailabilityCommand;
import com.example.domain.ProductAvailability;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-10
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductAvailabilityService {
	public boolean isProductAvailable(String productId) {
		ProductAvailability availability = new RetrieveProductAvailabilityCommand(productId).execute();
		return availability.isInStock();
	}
}
