package com.example.service;

import com.example.domain.ProductInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductCatalogService {
	private static List<ProductInfo> catalog;
	static {
		catalog = readCatalog();
	}
	public List<ProductInfo> fetchAll() {
		return catalog;
	}

	private static List<ProductInfo> readCatalog() {
		try {
			ClassLoader classLoader = ProductCatalogService.class.getClassLoader();
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