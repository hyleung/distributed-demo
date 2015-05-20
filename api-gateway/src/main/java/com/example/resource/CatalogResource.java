package com.example.resource;

import com.example.service.ProductCatalogService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/catalog")
public class CatalogResource {
	private final ProductCatalogService catalogService = new ProductCatalogService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveList() {
		try {
			return Response
					.ok(catalogService.fetchAll())
					.build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
