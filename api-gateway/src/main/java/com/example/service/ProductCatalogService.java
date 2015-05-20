package com.example.service;

import com.example.command.catalog.RetrieveCatalogCommand;
import com.example.command.catalog.RetrieveProductInfoCommand;
import com.example.domain.ProductInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductCatalogService {


	public ProductInfo retrieveProductInfo(final String id) {
		return new RetrieveProductInfoCommand(id).execute();
	}

	public List<ProductInfo> fetchAll() {
		return new RetrieveCatalogCommand().execute();
	}

}
