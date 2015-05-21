package com.example.command.catalog;

import com.example.command.CommandGroups;
import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-19
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveProductInfoCommand extends HystrixCommand<ProductInfo> {
	private final ProductCatalogDb database = new ProductCatalogDb();
	private final String id;
	public RetrieveProductInfoCommand(String id) {
		super(HystrixCommandGroupKey.Factory.asKey(CommandGroups.CATALOG));
		this.id= id;
	}

	@Override
	protected ProductInfo run() throws Exception {
		return database.retrieveProductInfo(id);
	}
}
