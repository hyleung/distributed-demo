package com.example.service;


import com.example.command.catalog.CatalogCommandFactory;
import com.example.command.inventory.RetrieveProductInventoryCommand;
import com.example.domain.ProductInfo;
import com.example.domain.ProductInventory;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-10
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductInventoryService {
	private final CatalogCommandFactory catalogCommandFactory;

	@Inject
	public ProductInventoryService(final CatalogCommandFactory catalogCommandFactory) {
		this.catalogCommandFactory = catalogCommandFactory;
	}

	public Optional<Boolean> isProductAvailable(final String productId) {
		//for some unknown reason, we fetch the product info (testing request caching)
		ProductInfo productInfo = catalogCommandFactory
											.retrieveProductInfoCommand(productId)
											.execute();
		Logger.getAnonymousLogger().log(Level.FINE,"Got info for: " + productInfo.getName());

		Optional<ProductInventory> availability = new RetrieveProductInventoryCommand(productId).execute();
		return availability.map(ProductInventory::isInStock);
	}
}
