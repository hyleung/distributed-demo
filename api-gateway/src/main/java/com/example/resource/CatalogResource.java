package com.example.resource;

import com.example.domain.ProductInfo;
import com.example.service.ProductCatalogService;
import com.google.inject.Inject;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.List;

import static ratpack.jackson.Jackson.json;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CatalogResource implements Handler {
	private final ProductCatalogService catalogService;

	@Inject
	public CatalogResource(final ProductCatalogService catalogService) {
		this.catalogService = catalogService;
	}

	private List<ProductInfo> retrieveList() {
		return catalogService.fetchAll();
	}

	@Override
	public void handle(Context ctx) throws Exception {
		ctx.render(json(retrieveList()));
	}
}
