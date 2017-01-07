package com.example.command.catalog;

import com.example.command.CommandGroups;
import com.example.database.ProductCatalogDb;
import com.example.domain.ProductInfo;
import com.github.kristofa.brave.LocalTracer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-19
 * Time: 6:55 PM
 * To change this template use File | Settings | File Templates.
 */
class RetrieveCatalogCommand extends HystrixCommand<List<ProductInfo>> {

	private final ProductCatalogDb database;
	private final LocalTracer localTracer;
	public RetrieveCatalogCommand(final ProductCatalogDb database, final LocalTracer localTracer) {
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey(CommandGroups.CATALOG))
				.andCommandKey(HystrixCommandKey.Factory.asKey("Retrieve Catalog")));
		this.database = database;
		this.localTracer = localTracer;
	}

	@Override
	protected List<ProductInfo> run() throws Exception {
		localTracer.startNewSpan("ProductCatalogDb", "fetchAll");
		List<ProductInfo> result = database.fetchAll();
		localTracer.finishSpan();
		return result;
	}
}
