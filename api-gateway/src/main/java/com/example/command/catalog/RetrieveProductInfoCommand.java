package com.example.command.catalog;

import com.example.command.CommandGroups;
import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-19
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
class RetrieveProductInfoCommand extends HystrixCommand<ProductInfo> {
	private final ProductCatalogDb database;

	private final String id;
	public RetrieveProductInfoCommand(final ProductCatalogDb database, final String id) {
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(CommandGroups.CATALOG))
				.andCommandKey(HystrixCommandKey.Factory.asKey("Product Info")));
		this.database = database;
		this.id= id;
	}

	@Override
	protected ProductInfo run() throws Exception {
		return database.retrieveProductInfo(id);
	}

	@Override
	protected String getCacheKey() {
		return this.id;
	}
}
