package com.example.service;

import com.example.command.catalog.CatalogCommandFactory;
import com.example.command.inventory.RetrieveStoreAvailabilityCommand;
import com.example.domain.ProductInfo;
import com.example.domain.StoreAvailability;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-16
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoreAvailabilityService {
	private final CatalogCommandFactory catalogCommandFactory;

	@Inject
	public StoreAvailabilityService(CatalogCommandFactory catalogCommandFactory) {
		this.catalogCommandFactory = catalogCommandFactory;
	}

	public Optional<List<StoreAvailability>> retrieveStoreAvailability(final String productId) {
		//for some unknown reason, we fetch the product info (testing request caching)
		ProductInfo productInfo = catalogCommandFactory.retrieveProductInfoCommand(productId).execute();
		Logger.getAnonymousLogger().log(Level.FINE,"Got info for: " + productInfo.getName());
		return new RetrieveStoreAvailabilityCommand(productId).execute();
	}
}
