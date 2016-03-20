package com.example.service;

import com.example.command.catalog.CatalogCommandFactory;
import com.example.domain.ProductInfo;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductCatalogService {

    private final CatalogCommandFactory commandFactory;

    @Inject
    public ProductCatalogService(CatalogCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public ProductInfo retrieveProductInfo(final String id) {
		return commandFactory
                .retrieveProductInfoCommand(id)
                .execute();
	}

	public List<ProductInfo> fetchAll() {
		return commandFactory.retriveCatalogCommand().execute();
	}

}
