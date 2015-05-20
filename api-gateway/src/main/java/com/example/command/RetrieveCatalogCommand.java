package com.example.command;

import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-19
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveCatalogCommand extends HystrixCommand<List<ProductInfo>> {
	public static final String CATALOG_GROUP = "Catalog";
	private final ProductCatalogDb database = new ProductCatalogDb();
	public RetrieveCatalogCommand() {
		super(HystrixCommandGroupKey.Factory.asKey(CATALOG_GROUP));
	}

	@Override
	public List<ProductInfo> run() throws Exception {
		return database.fetchAll();
	}
}
