package com.example.service;

import com.example.command.inventory.RetrieveStoreAvailabilityCommand;
import com.example.domain.StoreAvailability;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-16
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoreAvailabilityService {
	public Optional<List<StoreAvailability>> retrieveStoreAvailability(String productId) {
		return new RetrieveStoreAvailabilityCommand(productId).execute();
	}
}
