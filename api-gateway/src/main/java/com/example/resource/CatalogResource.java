package com.example.resource;

import com.example.domain.ProductInfo;
import com.example.service.ProductCatalogService;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/catalog")
public class CatalogResource {
	private final ProductCatalogService catalogService;

	@Inject
	public CatalogResource(final ProductCatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductInfo> retrieveList() {
		return catalogService.fetchAll();
	}
}
