package com.example.resource;

import com.example.domain.ProductInfo;
import com.example.service.ProductAvailabilityService;
import com.example.service.ProductCatalogService;
import com.example.service.StoreAvailabilityService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * Created with IntelliJ IDEA.
 * Date: 15-05-09
 * Time: 1:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/product")
public class ProductInfoResource {
	private final ProductCatalogService catalogService = new ProductCatalogService();
	private final ProductAvailabilityService availabilityService = new ProductAvailabilityService();
	private final StoreAvailabilityService storeAvailabilityService = new StoreAvailabilityService();

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveInfo(@PathParam("id") String id, @QueryParam("storeAvailability") boolean checkStoreAvailability) {
		try {
			ProductInfo productInfo = catalogService.retrieveProductInfo(id);
			Optional<Boolean> productAvailable = availabilityService.isProductAvailable(id);
			if (productAvailable.isPresent()) {
				productInfo.setInStock(productAvailable.get());
			}
			if (checkStoreAvailability)
				productInfo.setStoreAvailabilityList(storeAvailabilityService.retrieveStoreAvailability(id));
			return Response
					.ok(productInfo)
					.build();
		} catch (NoSuchElementException notFound) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity("Product not found")
					.build();
		}
	}

}
