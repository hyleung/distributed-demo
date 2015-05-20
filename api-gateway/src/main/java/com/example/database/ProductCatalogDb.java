package com.example.database;

import com.example.domain.ProductInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-19
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductCatalogDb {
	private static List<ProductInfo> catalog;
	static {
		catalog = readCatalog();
	}

	public ProductInfo retrieveProductInfo(final String id) {
		//fake some latency fetching results
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Iterables.find(catalog, new Predicate<ProductInfo>() {
			@Override
			public boolean apply(ProductInfo input) {
				return input.getId().equals(id);
			}
		});
	}

	public List<ProductInfo> fetchAll() {
		return catalog;
	}

	private static List<ProductInfo> readCatalog() {
		try {
			ClassLoader classLoader = ProductCatalogDb.class.getClassLoader();
			InputStream catalogInputStream = classLoader.getResourceAsStream("catalog.json");
			if (catalogInputStream==null) {
				System.out.println("null");
			}
			ObjectMapper mapper = new ObjectMapper();
			Catalog catalog = mapper.readValue(catalogInputStream, Catalog.class);
			return catalog.productList;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<ProductInfo>();
		}
	}

	private static class Catalog {
		@JsonDeserialize(as= ArrayList.class, contentAs = ProductInfo.class)
		public List<ProductInfo> productList;
	}
}
