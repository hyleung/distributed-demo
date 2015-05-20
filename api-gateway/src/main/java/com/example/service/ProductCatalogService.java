package com.example.service;

import com.example.command.RetrieveCatalogCommand;
import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductCatalogService {
	private ProductCatalogDb database = new ProductCatalogDb();

	public ProductInfo retrieveProductInfo(final String id) {
		return database.retrieveProductInfo(id);
	}

	public List<ProductInfo> fetchAll() throws Exception {
		return new RetrieveCatalogCommand().run();
	}

}
